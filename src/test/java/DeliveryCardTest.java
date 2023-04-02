import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    DataGenerator generator = new DataGenerator();
    private static Faker faker;
    @BeforeAll
    static void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }
    String name = faker.name().fullName();
    String phone = faker.phoneNumber().phoneNumber();
    @BeforeEach
    void setUpConnection() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldCardTest() {
        $("[data-test-id=name] input").sendKeys(name);
        $("[data-test-id=phone] input").sendKeys(phone);
        $("[data-test-id=city] input").sendKeys(generator.generateCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(generator.generateDate(5));
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + generator.generateDate(5)));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(generator.generateDate(7));
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification']").shouldBe(visible,Duration.ofSeconds(5));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + generator.generateDate(7)));
    }
}

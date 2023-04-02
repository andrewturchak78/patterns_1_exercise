import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
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

    public static String generateName() {
        faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String generatePhone() {
        faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().phoneNumber();
    }

    @Test
    void shouldCardTest() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue(generateName());
        $("[data-test-id=phone] input").setValue(generatePhone());
        $("[data-test-id=city] input").setValue(generator.generateCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(generator.generateDate(5));
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + generator.generateDate(5)));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(generator.generateDate(7));
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification']").shouldBe(visible, Duration.ofSeconds(15));
        $$("button").find(exactText("Перепланировать")).click();
        $(".notification_status_ok").shouldBe(visible);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + generator.generateDate(7)));
    }
}

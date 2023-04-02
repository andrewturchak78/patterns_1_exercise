

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    public String generateDate(int addDays) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String generateCity() {
        List<String> cities = Arrays.asList("Майкоп", "Уфа", "Махачкала", "Сыктывкар", "Саранск", "Чита", "Краснодар", "Пермь", "Хабаровск", "Брянск");
        Random random = new Random();
        int randomitem = random.nextInt(cities.size());
        String randomElement = cities.get(randomitem);
        return randomElement;
    }

}

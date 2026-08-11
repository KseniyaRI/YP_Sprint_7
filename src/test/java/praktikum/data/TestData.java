package praktikum.data;

import net.datafaker.Faker;

import java.util.List;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import praktikum.models.Courier;
import praktikum.models.Order;

public class TestData {

    private static final Faker FAKER = new Faker(Locale.ENGLISH);

    private TestData() {

    }

    public static Courier randomCourier() {
        String login = FAKER.name().username() + "_" + UUID.randomUUID();
        String password = FAKER.internet().password();
        String firstName = FAKER.name().firstName();

        return new Courier(login, password, firstName);
    }

    public static Order randomOrder(List<String> color) {
        String firstName = FAKER.name().firstName();
        String lastName = FAKER.name().lastName();
        String address = FAKER.address().fullAddress();
        String metroStation = String.valueOf(FAKER.number().numberBetween(1, 20));
        String phone = "89" + FAKER.number().digits(9);        
        int rentTime = FAKER.number().numberBetween(1, 7);
        String deliveryDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        String comment = FAKER.lorem().sentence(3);

        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}

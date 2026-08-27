package praktikum.courier;

import praktikum.BaseTest;
import praktikum.steps.CourierSteps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import praktikum.models.Courier;
import praktikum.data.TestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourierCreateTest extends BaseTest {

    private final CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
    private String firstName;

    @BeforeEach
    public void prepareData() {
        Courier courier = TestData.randomCourier();
        login = courier.getLogin();
        password = courier.getPassword();
        firstName = courier.getFirstName();
    }

    @AfterEach
    public void cleanUp() {
        Response loginResponse = courierSteps.loginCourier(login, password);
        if (loginResponse.getStatusCode() == 200) {
            int courierId = loginResponse.jsonPath().getInt("id");
            courierSteps.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Можно создать курьера с валидными данными")
    public void createCourierWithAllFields() {
        Response response = courierSteps.createCourier(new Courier(login, password, firstName));
        Assertions.assertEquals(201, response.getStatusCode());
        Assertions.assertTrue(response.jsonPath().getBoolean("ok"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина")
    public void createCourierWithNoLogin() {
        Response response = courierSteps.createCourier(new Courier(null, password, firstName));
        Assertions.assertEquals(400, response.getStatusCode());
        Assertions.assertEquals("Недостаточно данных для создания учетной записи", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля")
    public void createCourierWithNoPassword() {
        Response response = courierSteps.createCourier(new Courier(login, null, firstName));
        Assertions.assertEquals(400, response.getStatusCode());
        Assertions.assertEquals("Недостаточно данных для создания учетной записи", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Нельзя создать двух курьеров с одинаковым логином")
    public void createCourierWithExistingLogin() {
        Courier courier = new Courier(login, password, firstName);

        Response firstResponse = courierSteps.createCourier(courier);
        Assertions.assertEquals(201, firstResponse.getStatusCode());

        Response secondResponse = courierSteps.createCourier(courier);
        Assertions.assertEquals(409, secondResponse.getStatusCode());
        Assertions.assertEquals(
                "Этот логин уже используется. Попробуйте другой.",
                secondResponse.jsonPath().getString("message")
        );
    }
}

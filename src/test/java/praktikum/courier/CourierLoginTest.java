package praktikum.courier;

import praktikum.BaseTest;
import praktikum.data.TestData;
import praktikum.models.Courier;
import praktikum.steps.CourierSteps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.UUID;

public class CourierLoginTest extends BaseTest {

    private final CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
    private Integer courierId;

    @BeforeEach
    public void prepareData() {
        courierId = null;

        Courier courier = TestData.randomCourier();
        login = courier.getLogin();
        password = courier.getPassword();

        Response createResponse = courierSteps.createCourier(courier);
        Assertions.assertEquals(201, createResponse.getStatusCode());
    }

    @AfterEach
    public void cleanUp() {
        // Если в тесте уже успешно залогинились — id уже есть, повторный login не нужен
        if (courierId == null) {
            Response loginResponse = courierSteps.loginCourier(login, password);
            if (loginResponse.getStatusCode() == 200) {
                courierId = loginResponse.jsonPath().getInt("id");
            }
        }

        if (courierId != null) {
            courierSteps.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Курьер может авторизоваться с валидными данными")
    public void loginCourierWithAllFields() {
        Response response = courierSteps.loginCourier(login, password);
        Assertions.assertEquals(200, response.getStatusCode());

        courierId = response.jsonPath().getInt("id");
        Assertions.assertTrue(courierId > 0);
    }

    @Test
    @DisplayName("Нельзя авторизоваться без логина")
    public void loginCourierWithoutLogin() {
        Response response = courierSteps.loginCourier(null, password);
        Assertions.assertEquals(400, response.getStatusCode());
        Assertions.assertEquals("Недостаточно данных для входа", response.jsonPath().getString("message"));
    }

    // Известный баг API: при отсутствии password стенд отвечает 504 вместо 400
    @Test
    @DisplayName("Нельзя авторизоваться без пароля")
    public void loginCourierWithoutPassword() {
        Response response = courierSteps.loginCourier(login, null);
        Assertions.assertEquals(400, response.getStatusCode());
        Assertions.assertEquals("Недостаточно данных для входа", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться с несуществующим логином")
    public void loginCourierWithWrongLogin() {
        // Несуществующий login: на этом API тот же ответ, что у «несуществующей пары» в доке (404)
        String unknownLogin = "unknown_" + UUID.randomUUID();
        Response response = courierSteps.loginCourier(unknownLogin, password);
        Assertions.assertEquals(404, response.getStatusCode());
        Assertions.assertEquals("Учетная запись не найдена", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неверным паролем")
    public void loginCourierWithWrongPassword() {
        String wrongPassword = "wrong_password_" + UUID.randomUUID();
        Response response = courierSteps.loginCourier(login, wrongPassword);
        Assertions.assertEquals(404, response.getStatusCode());
        Assertions.assertEquals("Учетная запись не найдена", response.jsonPath().getString("message"));
    }
}

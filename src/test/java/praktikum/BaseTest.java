package praktikum;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    public void setUp() {
        // reset сбрасывает старые фильтры, иначе AllureRestAssured накапливается
        // и в отчёте появляется много одинаковых Request/Response
        RestAssured.reset();
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new AllureRestAssured());
    }
}

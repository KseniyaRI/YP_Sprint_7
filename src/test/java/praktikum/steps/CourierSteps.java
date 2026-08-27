package praktikum.steps;

import praktikum.models.Courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.RestAssured;

public class CourierSteps {

    @Step("Создать курьера")
    public Response createCourier(Courier courier) {
        return RestAssured.given()
                .header("Content-type", "application/json") 
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Залогинить курьера")
    public Response loginCourier(String login, String password) {
        Courier courier = new Courier(login, password, null);
        return RestAssured.given()
                .header("Content-type", "application/json") 
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }


    @Step("Удалить курьера с id = {id}")
    public Response deleteCourier(int id) {
        return RestAssured.given()
                .when()
                .delete("/api/v1/courier/{id}", id);
    }
}

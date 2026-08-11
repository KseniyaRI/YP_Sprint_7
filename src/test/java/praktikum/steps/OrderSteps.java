package praktikum.steps;

import praktikum.models.Order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.RestAssured;

public class OrderSteps {

    @Step("Создать заказ")
    public Response createOrder(Order order) {
        return RestAssured.given()
        .header("Content-type", "application/json") 
        .body(order)
        .when()
        .post("/api/v1/orders");
    }

    @Step("Получить список заказов")
    public Response getOrders() {
        return RestAssured.given()
        .when()
        .get("/api/v1/orders");
    }
}

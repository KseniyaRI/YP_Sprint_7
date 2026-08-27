package praktikum.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import praktikum.BaseTest;
import praktikum.steps.OrderSteps;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class OrderListTest extends BaseTest {

    private final OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("В ответе возвращается непустой список заказов")
    public void getOrders() {
        Response response = orderSteps.getOrders();
        response.then()
                .statusCode(200)
                .body("orders", hasSize(greaterThan(0)));
    }
}

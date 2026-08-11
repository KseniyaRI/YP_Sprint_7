package praktikum.order;

import org.junit.jupiter.api.Test;

import praktikum.BaseTest;
import praktikum.steps.OrderSteps;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest extends BaseTest {

    private final OrderSteps orderSteps = new OrderSteps();

    @Test
    public void getOrders() {
        Response response = orderSteps.getOrders();
        response.then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}

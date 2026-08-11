package praktikum.order;

import praktikum.BaseTest;
import praktikum.steps.OrderSteps;
import praktikum.models.Order;
import praktikum.data.TestData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

/**
 * Тесты ручки создания заказа: POST /api/v1/orders
 *
 * Что проверить по заданию (через параметризацию):
 * - один цвет BLACK;
 * - один цвет GREY;
 * - оба цвета;
 * - без цвета;
 * - в ответе есть track.
 *
 * Подсказка JUnit 5: @ParameterizedTest + @MethodSource (удобно для List&lt;String&gt; / null).
 */
public class OrderCreateTest extends BaseTest {

    private final OrderSteps orderSteps = new OrderSteps();

    static Stream<List<String>> colors() {
        return Stream.of(
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "GREY"),
                null
        );
    }

    @ParameterizedTest
    @MethodSource("colors")
    public void createOrderWithColors(List<String> color) {
        Order order = TestData.randomOrder(color);
        Response response = orderSteps.createOrder(order);
        Assertions.assertEquals(201, response.getStatusCode());
        Assertions.assertNotNull(response.jsonPath().get("track"));
    }
}

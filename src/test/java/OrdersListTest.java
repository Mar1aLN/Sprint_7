import Constants.ResponseCodes;
import Model.API.OrdersAPI;
import io.qameta.allure.Allure;
import jdk.jfr.Description;
import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;


public class OrdersListTest {

    @Test
    public void getListTest(){
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Проверка, что в тело ответа возвращается список заказов."));
        OrdersAPI.getList()
                .then()
                .assertThat()
                .statusCode(ResponseCodes.ORDERS_LIST_SUCCESS)
                .assertThat().body("orders", notNullValue());
    }
}

import Constants.ResponseCodes;
import Model.API.OrdersAPI;
import Model.Body.OrderCancelBody;
import Model.Body.OrderCreateBody;
import Model.Body.OrderCreateResponseBody;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private final OrderCreateBody orderCreateBody;

    private String track = null;

    public OrderCreateTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        orderCreateBody = new OrderCreateBody(firstName, lastName, address, metroStation,phone, rentTime,  deliveryDate, comment, color);
    }

    @Before
    public void setup(){
        track = null;
    }

    @Parameterized.Parameters()
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {       "Иван"
                        , "Иванов"
                        , "На деревню дедушке"
                        , "Сокольники"
                        , "8 800 555 75 35"
                        , 5
                        , "2022-01-01"
                        , "Комментарий"
                        , Arrays.asList("BLACK")
                },
                {       "Иван"
                        , "Иванов"
                        , "На деревню дедушке"
                        , "Сокольники"
                        , "8 800 555 75 35"
                        , 5
                        , "2022-01-01"
                        , "Комментарий"
                        , Arrays.asList("GRAY")
                },
                {       "Иван"
                        , "Иванов"
                        , "На деревню дедушке"
                        , "Сокольники"
                        , "8 800 555 75 35"
                        , 5
                        , "2022-01-01"
                        , "Комментарий"
                        , Arrays.asList("BLACK","GRAY")
                },
                {       "Иван"
                        , "Иванов"
                        , "На деревню дедушке"
                        , "Сокольники"
                        , "8 800 555 75 35"
                        , 5
                        , "2022-01-01"
                        , "Комментарий"
                        , Arrays.asList()
                },
        });
    }

    @Test
    public void orderCreateTest(){
        String sb = "Позитивная проверка создания заказа." +
                "color=" +
                orderCreateBody.getColor();

        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName((sb)));

        Response response = OrdersAPI.create(orderCreateBody);

        //Сохранение трека для отмены заказа после завершения теста
        track = response
                .as(OrderCreateResponseBody.class)
                .getTrack();

        response
                .then()
                .assertThat()
                .statusCode(ResponseCodes.ORDER_CREATE_SUCCESS)
                .assertThat()
                .body("track", notNullValue());
    }

    @After
    public void cleanup(){
        if(track != null){
            OrdersAPI.cancel(new OrderCancelBody(track));
        }
    }
}

package model.api;

import сonstants.Urls;
import model.body.OrderCancelBody;
import model.body.OrderCreateBody;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersAPI {
    private static final String ordersUrl = Urls.SCOOTER_BASE_URL + Urls.ORDERS_HANDLE;

    private static final String orderCancelUrl = Urls.SCOOTER_BASE_URL + Urls.ORDERS_CANCEL_HANDLE;

    public static Response create(OrderCreateBody orderCreateBody){
        return given()
                .header("Content-type", "application/json")
                .body(orderCreateBody)
                .post(ordersUrl);
    }

    public static Response getList(){
        return given()
                .queryParam("limit",2)
                .queryParam("page",0)
                .get(ordersUrl);
    }

    public static Response cancel(OrderCancelBody orderCancelBody){
        return given()
                .header("Content-type", "application/json")
                .body(orderCancelBody)
                .put(orderCancelUrl);
    }
}

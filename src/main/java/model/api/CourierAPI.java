package model.api;

import io.qameta.allure.Step;
import сonstants.Urls;
import model.body.CourierCreateBody;
import model.body.CourierLoginBody;
import model.body.CourierLoginResponseBody;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierAPI {
    private static final String courierDeleteUrl = Urls.SCOOTER_BASE_URL + Urls.COURIER_DELETE_HANDLE;

    private static final String courierCreateUrl = Urls.SCOOTER_BASE_URL + Urls.COURIER_CREATE_HANDLE;

    private static final String courierLoginUrl = Urls.SCOOTER_BASE_URL + Urls.COURIER_LOGIN_HANDLE;

    @Step("Удаление курьера")
    public static Response deleteCourier(int id){
        return given()
                .pathParam("id", id)
                .delete(courierDeleteUrl);
    }

    @Step("Логин курьера")
    public static Response login(CourierLoginBody courierLoginBody){
        return given()
                .header("Content-type", "application/json")
                .body(courierLoginBody)
                .when()
                .post(courierLoginUrl);

    }

    @Step("Создание курьера")
    public static Response createCourier(CourierCreateBody courierCreateBody){
        return given()
                .header("Content-type", "application/json")
                .body(courierCreateBody)
                .post(courierCreateUrl);

    }

    @Step("Удаление курьера, если возможен его логин")
    public static void deleteCourierIfExists(CourierLoginBody courierLoginBody){
        Integer id = CourierAPI.login(courierLoginBody)
                .body()
                .as(CourierLoginResponseBody.class)
                .getId();

        if(id != null){
            CourierAPI.deleteCourier(id);
        }
    }
}

package Model.API;

import Constants.Urls;
import Model.Body.CourierCreateBody;
import Model.Body.CourierLoginBody;
import Model.Body.CourierLoginResponseBody;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierAPI {
    private static final String courierDeleteUrl = Urls.SCOOTER_BASE_URL + Urls.COURIER_DELETE_HANDLE;

    private static final String courierCreateUrl = Urls.SCOOTER_BASE_URL + Urls.COURIER_CREATE_HANDLE;

    private static final String courierLoginUrl = Urls.SCOOTER_BASE_URL + Urls.COURIER_LOGIN_HANDLE;

    public static Response deleteCourier(int id){
        return given()
                .pathParam("id", id)
                .delete(courierDeleteUrl);
    }

    public static Response login(CourierLoginBody courierLoginBody){
        return given()
                .header("Content-type", "application/json")
                .body(courierLoginBody)
                .when()
                .post(courierLoginUrl);

    }

    public static Response createCourier(CourierCreateBody courierCreateBody){
        return given()
                .header("Content-type", "application/json")
                .body(courierCreateBody)
                .post(courierCreateUrl);

    }

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

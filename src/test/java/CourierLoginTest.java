import io.qameta.allure.internal.shadowed.jackson.databind.ser.Serializers;
import сonstants.Messages;
import сonstants.ResponseCodes;
import model.api.CourierAPI;
import model.body.CourierCreateBody;
import model.body.CourierLoginBody;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class CourierLoginTest extends BaseCourierTest {
    private static final String username = "tempUserName123";

    private static final String password = "12345";

    private static final String firstName = "Иван";

    public CourierLoginTest(){
        courierLoginBody = new CourierLoginBody(username, password);
    }

    @Test
    @DisplayName("Проверка корректного логина курьера")
    public void testLoginUser(){
        CourierCreateBody courierCreateBody = new CourierCreateBody(username, password, firstName);

        //Первичное создание курьера
        CourierAPI.createCourier(courierCreateBody);

        CourierAPI.login(new CourierLoginBody(username, password))
                .then()
                .assertThat()
                .statusCode(ResponseCodes.COURIER_LOGIN_SUCCESS)
                .assertThat()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Негативная проверка логина курьера, неверный пароль")
    public void testWrongPasswordLoginUser(){
        CourierCreateBody courierCreateBody = new CourierCreateBody(username, password, firstName);

        //Первичное создание курьера
        CourierAPI.createCourier(courierCreateBody);

        CourierAPI.login(new CourierLoginBody(username, "wrong"))
                .then()
                .assertThat()
                .statusCode(ResponseCodes.COURIER_LOGIN_NOT_FOUND)
                .assertThat()
                .body("message", equalTo(Messages.COURIER_LOGIN_NOT_FOUND));
    }

    @Test
    @DisplayName("Негативная проверка логина курьера, отсутствует пароль")
    public void testLoginMissingPassword(){
        CourierCreateBody courierCreateBody = new CourierCreateBody(username, password, firstName);

        //Первичное создание курьера
        CourierAPI.createCourier(courierCreateBody);

        CourierAPI.login(new CourierLoginBody(username, ""))
                .then()
                .assertThat()
                .statusCode(ResponseCodes.COURIER_LOGIN_MISSING_ARGUMENTS)
                .assertThat()
                .body("message", equalTo(Messages.COURIER_LOGIN_MISSING_ARGUMENTS));
    }

    @Test
    @DisplayName("Негативная проверка логина курьера, отсутствует логин")
    public void testLoginMissingUsername(){
        CourierCreateBody courierCreateBody = new CourierCreateBody(username, password, firstName);

        //Первичное создание курьера
        CourierAPI.createCourier(courierCreateBody);

        CourierAPI.login(new CourierLoginBody("", password))
                .then()
                .assertThat()
                .statusCode(ResponseCodes.COURIER_LOGIN_MISSING_ARGUMENTS)
                .assertThat()
                .body("message", equalTo(Messages.COURIER_LOGIN_MISSING_ARGUMENTS));
    }

}

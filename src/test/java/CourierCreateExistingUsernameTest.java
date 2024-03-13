import сonstants.Messages;
import model.api.CourierAPI;
import model.body.CourierCreateBody;
import model.body.CourierLoginBody;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class CourierCreateExistingUsernameTest extends BaseCourierTest{
    private final String username = "userTestCreate";

    private final String password = "123";

    private final String firstName = "Вася";
    public CourierCreateExistingUsernameTest(){
        courierLoginBody = new CourierLoginBody(username, password);
    }

    @Test
    @DisplayName("Проверка невозможности создать курьера с занятым логином")
    public void testCreateExistingUser(){
        CourierCreateBody courierCreateBody = new CourierCreateBody(username, password, firstName);

        //Создание курьера с тестовыми логином и паролем
        CourierAPI.createCourier(courierCreateBody);

        //Попытка создать курьера с занятым логином
        CourierAPI.createCourier(courierCreateBody)
                .then()
                .assertThat()
                .statusCode(409)
                .assertThat().body("message", equalTo(Messages.USERNAME_NOT_AVAILABLE));
    }
}

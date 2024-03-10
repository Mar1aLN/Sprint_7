import Constants.Messages;
import Model.API.CourierAPI;
import Model.Body.CourierCreateBody;
import Model.Body.CourierLoginBody;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class CourierCreateExistingUsernameTest {
    private final String username = "userTestCreate";

    private final String password = "123";

    private final String firstName = "Вася";

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

    @After
    public void cleanUp(){
        CourierAPI.deleteCourierIfExists(new CourierLoginBody(username, password));
    }
}

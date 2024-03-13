import io.qameta.allure.internal.shadowed.jackson.databind.ser.Serializers;
import сonstants.ResponseCodes;
import сonstants.Messages;
import сonstants.Urls;
import model.api.CourierAPI;
import model.body.CourierCreateBody;
import model.body.CourierLoginBody;

import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CourierCreateTest extends BaseCourierTest {
    private final boolean isValid;

    private final int responseCode;

    private final String responseControlKey;

    private final Object responseControlValue;

    private final CourierCreateBody courierCreateBody;

    private List<String> missingFields;

    public CourierCreateTest(String username, String password, String firstName, String responseControlKey, Object responseControlValue, boolean isValid, int responseCode) {
        this.responseControlKey = responseControlKey;
        this.responseControlValue = responseControlValue;

        this.isValid = isValid;
        this.responseCode = responseCode;

        courierCreateBody = new CourierCreateBody(username, password, firstName);
        courierLoginBody = new CourierLoginBody(username, password);

        missingFields = new ArrayList<>();

        if(username.isEmpty()){
            missingFields.add("username");
        }
        if(password.isEmpty()){
            missingFields.add("password");
        }
        if(firstName.isEmpty()){
            missingFields.add("firstName");
        }

    }


    @Parameterized.Parameters(name = "username = {0}, password={1}, firstName={2}, позитивная проверка={5}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {       "MLN_username123"
                        , "12345"
                        , "Иван"
                        , "ok"
                        , true
                        , true
                        , ResponseCodes.COURIER_CREATE_SUCCESS
                },
                {       "MLN_username444"
                        , "12345"
                        , "John22"
                        , "ok"
                        , true
                        , true
                        , ResponseCodes.COURIER_CREATE_SUCCESS
                },
                {       "MLN_username444"
                        , ""
                        , "Семен"
                        , "message"
                        , Messages.NOTE_ENOUGH_PARAMETERS_TO_CREATE_USER
                        , false
                        , ResponseCodes.COURIER_CREATE_NOT_ENOUGH_PARAMETERS
                },
                {       ""
                        , "2222123"
                        , "Петр"
                        , "message"
                        , Messages.NOTE_ENOUGH_PARAMETERS_TO_CREATE_USER
                        , false
                        , ResponseCodes.COURIER_CREATE_NOT_ENOUGH_PARAMETERS
                },
                {       "MLN_username444"
                        , "12345"
                        , ""
                        , "message"
                        , Messages.NOTE_ENOUGH_PARAMETERS_TO_CREATE_USER
                        , false
                        , 400
                }
        });
    }


    @Test
    @Description("Создание курьера")
    public void courierCreateResponseBodyTest(){


        StringBuilder testInfo = new StringBuilder();
        if(isValid){
            testInfo.append("Позитивная ");
        } else {
            testInfo.append("Негативная ");
        }
        testInfo.append("проверка создания курьера.");
        testInfo.append("body=");
        testInfo.append(new Gson().toJson(courierCreateBody));

        Response response = CourierAPI.createCourier(courierCreateBody);

        //Формирование аннтоации allure
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName((testInfo.toString())));

        //Создание курьера, проверка кода и тела ответа
        response
                .then()
                .assertThat()
                .statusCode(responseCode)
                .assertThat().body(responseControlKey, equalTo(responseControlValue));
    }


}

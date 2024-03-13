import model.api.CourierAPI;
import model.body.CourierLoginBody;
import org.junit.After;
import org.junit.Before;

public class BaseCourierTest {
    CourierLoginBody courierLoginBody;

    @Before
    public void setup(){
        CourierAPI.deleteCourierIfExists(courierLoginBody);
    }

    @After
    public void cleanUp(){
        CourierAPI.deleteCourierIfExists(courierLoginBody);
    }
}

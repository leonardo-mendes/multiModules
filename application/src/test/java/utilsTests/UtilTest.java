package utilsTests;

import domains.Car;
import domains.request.CarRequest;
import domains.response.CarResponse;

public class UtilTest {

    public Car buildValidCar() {
        return new Car("Valid Car");
    }

    public CarRequest buildValidCarRequest() {
        return new CarRequest("Valid Car");
    }

    public CarResponse buildValidCarResponse() {
        return new CarResponse("Valid Car");
    }

}

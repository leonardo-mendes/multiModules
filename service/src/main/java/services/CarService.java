package services;

import domains.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import repositories.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car insert(Car car) {
        return carRepository.save(car);
    }

    public Car findById(Integer id) {
        Optional<Car> carUpdate = carRepository.findById(id);

        carUpdate.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Car not found with id " + id));

        return carUpdate.get();
    }

    public Car update(Car car) {
        Optional<Car> carUpdate = carRepository.findById(car.getId());
        Car carUpdated = new Car();

        carUpdate.ifPresent(carOptional -> {
            carUpdated.setName(carOptional.getName());
            carUpdated.setId(carOptional.getId());
        });
        carUpdate.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Car not found with id " + car.getId()));


        return carUpdated;
    }

    public void delete(Integer id) {
        Optional<Car> carUpdate = carRepository.findById(id);

        carUpdate.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Car not found with id " + id));

        carRepository.delete(carUpdate.get());
    }

}

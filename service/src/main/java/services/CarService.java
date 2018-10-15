package services;

import domains.Car;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        if(car == null){
            throw new IllegalArgumentException("The request is invalid!");
        }

        return carRepository.save(car);
    }

    public Car findById(Integer id) {
        Optional<Car> carUpdate = carRepository.findById(id);

        carUpdate.orElseThrow(() -> new ObjectNotFoundException(id, "Car not found with id "));

        return carUpdate.get();
    }

    public Car update(Integer id, Car car) {
        if(car == null){
            throw new IllegalArgumentException("The request is invalid!");
        }

        Optional<Car> carUpdate = carRepository.findById(id);
        Car carUpdated = new Car();

        carUpdate.ifPresent(carOptional -> {
            carUpdated.setName(car.getName());
            carUpdated.setId(id);
        });
        carUpdate.orElseThrow(() -> new ObjectNotFoundException(id, "Car not found with id "));

        return carRepository.save(carUpdated);
    }

    public void delete(Integer id) {
        Optional<Car> carUpdate = carRepository.findById(id);

        carUpdate.orElseThrow(() -> new ObjectNotFoundException(id, "Car not found with id "));

        carRepository.delete(carUpdate.get());
    }

}

package applicationTests;

import configTests.ConfigTests;
import domains.Car;
import org.hibernate.ObjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import repositories.CarRepository;
import services.CarService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigTests.class)
public class CarServiceTest {

    @InjectMocks
    private CarService carService;
    @Mock
    private CarRepository carRepository;

    private Optional<Car> buildValidCar() {
        return Optional.of(new Car("Valid Car"));
    }

    private List<Car> buildListOfValidCar() {
        return Arrays.asList(new Car("Valid Car"));
    }

    @Test
    public void ShouldListAllCars() {
        when(carRepository.findAll()).thenReturn(buildListOfValidCar());
        List<Car> cars = carService.findAll();
        assertEquals(cars.get(0).getName(), buildValidCar().get().getName());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void ShouldFindAValidCarById() {
        when(carRepository.findById(anyInt())).thenReturn(buildValidCar());
        Car car = carService.findById(1);
        assertEquals(car.getName(), buildValidCar().get().getName());
        verify(carRepository, times(1)).findById(anyInt());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void ShouldNotFindAValidCarById() {
        when(carRepository.findById(anyInt())).thenReturn(Optional.empty());
        Car car = carService.findById(1);
        verify(carRepository, times(1)).findById(anyInt());
    }

    @Test
    public void ShouldInsertACar() {
        when(carRepository.save(any())).thenReturn(buildValidCar().get());
        Car car = carService.insert(buildValidCar().get());
        assertEquals(car.getName(), buildValidCar().get().getName());
        verify(carRepository, times(1)).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldNotInsertACar() {
        carService.insert(null);
    }

    @Test
    public void ShouldUpdateACar() {
        when(carRepository.findById(anyInt())).thenReturn(buildValidCar());
        when(carRepository.save(any())).thenReturn(buildValidCar().get());
        Car car = carService.update(1, buildValidCar().get());
        assertEquals(car.getName(), buildValidCar().get().getName());
        verify(carRepository, times(1)).findById(anyInt());
        verify(carRepository, times(1)).save(any());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void ShouldNotUpdateWithInvalidId() {
        when(carRepository.findById(anyInt())).thenReturn(buildValidCar());
        carService.update(null, buildValidCar().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldNotUpdateWithInvalidCar() {
        carService.update(1, null);
    }

    @Test
    public void ShouldDeleteACar() {
        when(carRepository.findById(anyInt())).thenReturn(buildValidCar());
        doNothing().when(carRepository).delete(any());
        carService.delete(1);
        verify(carRepository, times(1)).findById(anyInt());
        verify(carRepository, times(1)).delete(any());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void ShouldNotDeleteWithInvalidId() {
        when(carRepository.findById(anyInt())).thenReturn(Optional.empty());
        carService.delete(1);
    }




}

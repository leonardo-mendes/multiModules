package controllers;

import domains.Car;
import domains.request.CarRequest;
import domains.response.CarResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.CarService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(value = "/car")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CarResponse> findAll() {
        List<CarResponse> carResponses = new ArrayList<>();
        carService.findAll().forEach(cars -> carResponses.add(modelMapper.map(cars, CarResponse.class)));
        return carResponses;
    }

    @GetMapping
    @RequestMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarResponse findById(@PathVariable Integer id) {
        Car carResponse = carService.findById(id);
        return modelMapper.map(carResponse, CarResponse.class);
    }

    @PutMapping
    @RequestMapping(value = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarResponse update(@PathVariable Integer id, @RequestBody CarRequest carRequest) {
        return modelMapper.map(carService.update(id, modelMapper.map(carRequest, Car.class)), CarResponse.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CarResponse insert(@Valid @RequestBody CarRequest carRequest) throws IOException, TimeoutException {
        return modelMapper.map(carService.insert(modelMapper.map(carRequest, Car.class)), CarResponse.class);
    }

    @DeleteMapping
    @RequestMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        carService.delete(id);
    }

}

package controllers;

import domains.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import services.CarService;

import java.util.List;

@RestController(value = "/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public List<Car> findAll(){
        return carService.findAll();
    }
}

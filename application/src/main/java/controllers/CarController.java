package controllers;

import domains.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.CarService;

import java.util.List;

@RestController
@RequestMapping(value = "/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Car> findAll(){
        return carService.findAll();
    }

    @GetMapping
    @RequestMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Car findById(@PathVariable Integer id){
        return carService.findById(id);
    }

    @PutMapping
    @RequestMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Car update(@PathVariable Car car){
        return carService.update(car);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Car insert(@PathVariable Car car){
        return carService.insert(car);
    }

    @DeleteMapping
    @RequestMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        carService.delete(id);
    }

}

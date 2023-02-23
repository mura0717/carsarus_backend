package dat3.cars.api;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cars")
public class CarController {
    CarService carService;

    //Anonymous
    @GetMapping
    List<CarResponse> getCars() {
        return carService.getAllCars();
    }

    //Anonymous
    @GetMapping(path = "/{carnumber}")
    CarResponse getCarById(@PathVariable long id) {
        return carService.findCarById(id);
    }

    //Admin
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CarResponse addCar(@RequestBody CarRequest body) {
        return carService.addNewCar(body);
    }

    //Admin
    @PutMapping("id")
    CarRequest editCarById (@PathVariable long id){
        return carService.updateCar(id);
    }

    //Admin
    @DeleteMapping("id")
    public void deleteCarById(@PathVariable long id){
        carService.deleteCar(id);
    }



}

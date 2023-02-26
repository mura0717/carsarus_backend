package dat3.cars.api;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/cars")
@RestController
public class CarController {

    CarService carService;

    //Security
    @GetMapping
    List<CarResponse> getCars() {
        return carService.getAllCars(false);
    }

    //Anonymous
    @GetMapping(path = "/{id}")
    CarResponse getCarById(@PathVariable long id) {
        return carService.findCarById(id, false);
    }

    //Admin
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CarResponse addCar(@RequestBody CarRequest body) {
        return carService.addNewCar(body);
    }

    //Admin
    @PutMapping("/{id}")
    CarResponse editCarById(@RequestBody CarRequest body, @PathVariable long id) {
        return carService.updateCar(body, id);
    }

    //Admin
    @DeleteMapping("id")
    public void deleteCarById(@PathVariable long id) {
        carService.deleteCar(id);
    }


}

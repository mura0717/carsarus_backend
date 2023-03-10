package dat3.cars.api;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/cars")
@RestController
@CrossOrigin
public class CarController {

    CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    //Security
    @GetMapping
    List<CarResponse> getCars() {
        return carService.getCars(false);
    }

    @GetMapping("/admin")
    List<CarResponse> getCarsAdmin() {
        return carService.getCars(true);
    }

    //Security Anonymous
    @GetMapping(path = "/{id}")
    CarResponse getCarById(@PathVariable int id) {
        return carService.findCarById(id, false);
    }

    //Admin
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CarResponse addCar(@RequestBody CarRequest body) {
        return carService.addNewCar(body);
    }

    //Admin
    @PutMapping("/{id}")
    CarResponse editCarById(@RequestBody CarRequest body, @PathVariable int id) {
        return carService.editCar(body, id);
    }

    //Admin
    @DeleteMapping("id")
    public void deleteCarById(@PathVariable int id) {
        carService.deleteCar(id);
    }


}

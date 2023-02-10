package dat3.cars.api;

import dat3.cars.dto.CarResponse;
import dat3.cars.dto.MemberResponse;
import dat3.cars.service.CarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/cars")
public class CarController {
    CarService carService;

    @GetMapping
    List<CarResponse> getCars() {
        return carService.getCars(true);
    }


    @GetMapping(path = "/{carnumber}")
    CarResponse getCarById(@PathVariable long id) {
        return carService.findCarById(id);
    }

}

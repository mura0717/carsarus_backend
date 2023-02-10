package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarService {

    CarRepository carRepository;
    CarRequest carRequest;
    CarResponse carResponse;

    public void CarService (CarRepository carRepository){
        this.carRepository = carRepository;
    }

    public List<CarResponse> getCars (boolean includeAll) {
        List<Car> cars = carRepository.findAll();
        List<CarResponse> carResponses = cars.stream().map((c) -> new CarResponse(c)).toList();
        return carResponses;
    }


    public CarResponse findCarById(long id) {
        Car found = carRepository.findById(String.valueOf(id)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Car not found"));

        return null;
    }
}

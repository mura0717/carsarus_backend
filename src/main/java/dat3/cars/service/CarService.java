package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Getter
@Setter
@Service
public class CarService {

    CarRepository carRepository;
    CarRequest carRequest;
    CarResponse carResponse;

    public void CarService (CarRepository carRepository){
        this.carRepository = carRepository;
    }

    public List<CarResponse> getCars () {
        List<Car> cars = carRepository.findAll();
        List<CarResponse> carResponses = cars.stream().map((c) -> new CarResponse(c)).toList();
        return carResponses;
    }

    public CarResponse findCarById(long id) {
        Car found = carRepository.findById(String.valueOf(id)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Car not found"));
        return new CarResponse(found);
    }

    public CarResponse addNewCar(CarRequest carRequest){
        Car newCar = carRepository.save(new Car(carRequest.getId(), carRequest.getBrand(), carRequest.getModel(), carRequest.getPricePrDay(), carRequest.getBestDiscount()));
        return new CarResponse(newCar);
    }

/*    public CarRequest editCar(long id){
        CarResponse found = findCarById(id);
        Car updatedCar = carRepository.save(found.setBrand(), found.setModel(), found.setPricePrDay(), found.setBestDiscount());

        return new CarRequest(updatedCar);
    }*/

    public CarRequest editCar(long id) {
        CarResponse found = findCarById(id);
        Car updatedCar = carRepository.save(new Car(found.getId(), found.getBrand(), found.getModel(), found.getPricePrDay(), found.getBestDiscount()));
        return new CarRequest(updatedCar);
    }

    public CarResponse deleteCar(long id){
        List<Car> cars = carRepository.findAll();
        CarResponse found = findCarById(id);
        cars.remove(found);
        return (CarResponse) cars; //?
    }
}

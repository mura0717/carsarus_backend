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

    public List<CarResponse> getAllCars() {
        List<Car> cars = carRepository.findAll();
        List<CarResponse> carResponses = cars.stream().map((c) -> new CarResponse(c)).toList();
        return carResponses;
    }

    public CarResponse findCarById(long id) {
        Car found = carRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Car not found."));
        return new CarResponse(found);
    }

    public CarResponse addNewCar(CarRequest carRequest){
        if(carRepository.existsById(carRequest.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Car with this ID already exists.");
        }

        Car newCar = carRepository.save(new Car(carRequest.getId(), carRequest.getBrand(), carRequest.getModel(), carRequest.getPricePrDay(), carRequest.getBestDiscount()));
        return new CarResponse(newCar);
    }

    public CarRequest updateCar(long id) {
        Car found = carRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Car not found."));
        found.setBrand(carRequest.getBrand());
        found.setModel(carRequest.getModel());
        found.setPricePrDay(carRequest.getPricePrDay());
        found.setBestDiscount(carRequest.getBestDiscount());
        carRepository.save(found);
        return carRequest;
    }


    public void deleteCar(long id){
        Car found = carRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Car not found"));
        carRepository.delete(found);
    }

}

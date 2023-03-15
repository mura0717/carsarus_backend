package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService (CarRepository carRepository){
        this.carRepository = carRepository;
    }

    public List<CarResponse> getCars(boolean includeAll) {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(car -> new CarResponse(car, includeAll)).collect(Collectors.toList());
//        List<CarResponse> carResponses = cars.stream().map((c) -> new CarResponse(c, includeAll)).toList();
//        return carResponses;
    }

    public CarResponse findCarById(long id, boolean includeAll) {
        Car found = carRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Car not found."));
        return new CarResponse(found, includeAll);
    }

    public CarResponse addNewCar(CarRequest carRequest){
        if(carRepository.existsById(carRequest.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Car with this ID already exists.");
        }

        Car newCar = new Car();
                newCar.setId(carRequest.getId());
                newCar.setBrand(carRequest.getBrand());
                newCar.setModel(carRequest.getModel());
                newCar.setPricePrDay(carRequest.getPricePrDay());
                newCar.setBestDiscount(carRequest.getBestDiscount());
        newCar = carRepository.save(newCar);
        return new CarResponse(newCar, true);
    }

    public CarResponse editCar(CarRequest body, long id) {
        Car carToEdit = carRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Car not found."));
        carToEdit.setBrand(body.getBrand());
        carToEdit.setModel(body.getModel());
        carToEdit.setPricePrDay(body.getPricePrDay());
        carToEdit.setBestDiscount(body.getBestDiscount());
        Car updatedCar = carRepository.save(carToEdit);
        return new CarResponse(updatedCar, true);
    }


    public void deleteCar(long id){
        Car found = carRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Car not found"));
        carRepository.delete(found);
    }

}

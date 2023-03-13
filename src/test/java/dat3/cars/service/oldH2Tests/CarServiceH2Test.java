/*
package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarServiceH2Test {

    @Autowired
    CarRepository carRepository;
    CarService carService;

    boolean dataInitialized = true;

    private Car car1;
    private Car car2;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        if(!dataInitialized) return;
        car1 = Car.builder().brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        car2 = Car.builder().brand("BMW").model("M3").pricePrDay(200000).bestDiscount(20).build();
        carRepository.saveAndFlush(car1);
        carRepository.saveAndFlush(car2);
        carService = new CarService(carRepository);
        dataInitialized = true;
    }

    @Test
    void testGetCarsAdmin() {
        List<CarResponse> carsResponseAdmin = carService.getCars(true);
        assertEquals(2, carsResponseAdmin.size());
        assertEquals(100000, carsResponseAdmin.get(0).getPricePrDay());
        assertEquals(20, carsResponseAdmin.get(1).getBestDiscount());

    }
    @Test
    void testGetCarsUser(){
        List<CarResponse> carsResponseUser = carService.getCars(false);
        assertEquals(2, carsResponseUser.size());
        assertEquals("Audi", carsResponseUser.get(0).getBrand());
        assertEquals("M3", carsResponseUser.get(1).getModel());
        assertEquals(100000, carsResponseUser.get(0).getPricePrDay());
        assertNull(carsResponseUser.get(0).getBestDiscount());

    }
    @Test
    void testFindCarByIdbyAdmin() {
        var cars = carRepository.findAll(); //id check for cars
        CarResponse foundCar = carService.findCarById(13, true);

        assertEquals(13, foundCar.getId());
        assertEquals("Audi", foundCar.getBrand());
        assertEquals("A4", foundCar.getModel());
        assertEquals(100000, foundCar.getPricePrDay());
        assertEquals(10, foundCar.getBestDiscount());
    }

    @Test
    void testFindCarByIdbyUser() {
        var cars = carRepository.findAll(); //id check for cars
        CarResponse foundCar = carService.findCarById(11, false);

        assertEquals(11, foundCar.getId());
        assertEquals("Audi", foundCar.getBrand());
        assertEquals("A4", foundCar.getModel());
        assertEquals(100000, foundCar.getPricePrDay());
        assertNull(foundCar.getBestDiscount());
    }

    @Test
    void testAddNewCar() {
        CarRequest carRequest = CarRequest.builder().brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        CarResponse addedCar = carService.addNewCar(carRequest);

        assertNotNull(addedCar.getId());
        assertEquals(19, addedCar.getId()); //Is the Id becomes 3 after the first 2 cars because of before each?
        assertEquals("Audi", addedCar.getBrand());
        assertEquals("A4", addedCar.getModel());
        assertEquals(100000, addedCar.getPricePrDay());
        assertEquals(10, addedCar.getBestDiscount());
    }

    @Test
    void testEditCar() {
        CarRequest editCarRequest = CarRequest.builder().brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        CarResponse retrievedCar = carService.editCar(editCarRequest, car2.getId());

        assertNotNull(retrievedCar);
        assertEquals("Audi", retrievedCar.getBrand());
        assertEquals("A4", retrievedCar.getModel());
        assertEquals(100000, retrievedCar.getPricePrDay());
        assertEquals(10, retrievedCar.getBestDiscount());
    }

    @Test
    void testDeleteCar() {
        carRepository.deleteById(car1.getId());
        Optional<Car> retrievedCar = carRepository.findById(car1.getId());
        assertFalse(retrievedCar.isPresent());
    }

}*/

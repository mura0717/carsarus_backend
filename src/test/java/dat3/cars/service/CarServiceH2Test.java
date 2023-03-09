package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
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

    boolean dataInitialized = false;

    private Car car1;
    private Car car2;

    @BeforeEach
    void setUp() {
        if(dataInitialized) return;
        car1 = Car.builder().id(1).brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        car2 = Car.builder().id(2).brand("BMW").model("M3").pricePrDay(200000).bestDiscount(20).build();
        carRepository.saveAndFlush(car1);//Flush?
        carRepository.saveAndFlush(car2);
        carService = new CarService(carRepository);

        dataInitialized = true;
    }

    @Test
    void testGetCarsAdmin() {
        List<CarResponse> carsResponseAdmin = carService.getCars(true);
        System.out.println(carsResponseAdmin);
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
        CarResponse foundCar = carService.findCarById(1L, true);

        assertEquals(1, foundCar.getId());
        assertEquals("Audi", foundCar.getBrand());
        assertEquals("A4", foundCar.getModel());
        assertEquals(100000, foundCar.getPricePrDay());
        assertEquals(10, foundCar.getBestDiscount());
    }

    @Test
    void testFindCarByIdbyUser() {
        CarResponse foundCar = carService.findCarById(1L, false);

        assertEquals(1, foundCar.getId());
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
        assertEquals(3L, addedCar.getId()); //Is the Id becomes 3 after the first 2 cars because of before each?
        assertEquals("Audi", addedCar.getBrand());
        assertEquals("A4", addedCar.getModel());
        assertEquals(100000, addedCar.getPricePrDay());
        assertEquals(10, addedCar.getBestDiscount());
    }

    @Test
    void testEditCar() {
       /* Car car1 = new Car(1L, "Audi", "A4", 100000, 10, LocalDateTime.now(), null,new ArrayList<>());
        carRepository.save(car1);

        Car updatedCar1 = new Car(car1.getId(), "BMW", "M3", 200000, 20, car1.getCreated(), LocalDateTime.now(),new ArrayList<>());
        carRepository.save(updatedCar1);*/
        CarRequest editCarRequest = CarRequest.builder().brand(car1.getBrand()).model(car1.getModel()).pricePrDay(car1.getPricePrDay()).bestDiscount(car1.getBestDiscount()).build();
        CarResponse retrievedCar = carService.editCar(editCarRequest, car1.getId());

        //assertTrue(retrievedCar.isPresent());
        assertEquals("BMW", retrievedCar.getBrand());
        assertEquals("M3", retrievedCar.getModel());
        assertEquals(200000, retrievedCar.getPricePrDay());
        assertEquals(20, retrievedCar.getBestDiscount());
    }

    @Test
    void testDeleteCar() {
        carRepository.deleteById(car1.getId());
        Optional<Car> retrievedCar = carRepository.findById(1L);
        assertFalse(retrievedCar.isPresent());
    }
}
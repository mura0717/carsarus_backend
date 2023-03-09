package dat3.cars.service;

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
        carRepository.saveAndFlush(car1);
        carRepository.saveAndFlush(car2);
        carService = new CarService(carRepository);

        dataInitialized = true;
    }

    @Test
    void testGetAllCars() {
        List<CarResponse> carsResponseAdmin = carService.getCars(true);
        System.out.println(carsResponseAdmin);
        assertEquals(2, carsResponseAdmin.size());
        assertEquals(100000, carsResponseAdmin.get(0).getPricePrDay());
        assertNotNull("BMW", carsResponseAdmin.get(1).getBrand());

        List<CarResponse> carsResponseUser = carService.getCars(false);
        assertEquals(2, carsResponseUser.size());
        assertNull("Audi", carsResponseUser.get(0).getBrand());
        assertNull("BMW", carsResponseUser.get(1).getBrand());

    }

    @Test
    void testFindCarById() {
        CarResponse foundCar = carService.findCarById(1L, false);

        assertEquals(1, foundCar.getId());
        assertEquals("Audi", foundCar.getBrand());
        assertEquals("A4", foundCar.getModel());
        assertEquals(100000, foundCar.getPricePrDay());
        assertEquals(10, foundCar.getBestDiscount());
    }

    @Test
    void testAddNewCar() {
        Car car3 = new Car(1L, "Audi", "A4", 100000, 10, LocalDateTime.now(), null,new ArrayList<>());
        carRepository.save(car3);

        assertNotNull(car3.getId());
        assertEquals(1L, car3.getId());
        assertEquals("Audi", car3.getBrand());
        assertEquals("A4", car3.getModel());
        assertEquals(100000, car3.getPricePrDay());
        assertEquals(10, car3.getBestDiscount());
    }

    @Test
    void testUpdateCar() {
        Car car1 = new Car(1L, "Audi", "A4", 100000, 10, LocalDateTime.now(), null,new ArrayList<>());
        carRepository.save(car1);

        Car updatedCar1 = new Car(car1.getId(), "BMW", "M3", 200000, 20, car1.getCreated(), LocalDateTime.now(),new ArrayList<>());
        carRepository.save(updatedCar1);

        Optional<Car> retrievedCar = carRepository.findById(1L);
        assertTrue(retrievedCar.isPresent());
        assertEquals("BMW", retrievedCar.get().getBrand());
        assertEquals("M3", retrievedCar.get().getModel());
        assertEquals(200000, retrievedCar.get().getPricePrDay());
        assertEquals(20, retrievedCar.get().getBestDiscount());
    }

    @Test
    void testDeleteCar() {
        Car car1 = new Car(1L, "Audi", "A4", 100000, 10, LocalDateTime.now(), null,new ArrayList<>());
        carRepository.save(car1);

        carRepository.deleteById(1L);
        Optional<Car> retrievedCar = carRepository.findById(1L);
        assertFalse(retrievedCar.isPresent());
    }
}
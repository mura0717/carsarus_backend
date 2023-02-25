package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarServiceMockitoTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car car1;
    private Car car2;
    List<Car> dummyCars = new ArrayList<>();

    @BeforeEach
    void setUp() {

        car1 = Car.builder().id(1).brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        car2 = Car.builder().id(2).brand("BMW").model("M3").pricePrDay(200000).bestDiscount(20).build();
        car1.setCreated(LocalDateTime.now());
        car2.setCreated(LocalDateTime.now());
        dummyCars.add(car1);
        dummyCars.add(car2);
    }

    @Test
    void testGetAllCars() {

        List<CarResponse> cars = carService.getAllCars(false);
        Mockito.when(carRepository.findAll()).thenReturn(dummyCars);


        assertEquals(2, cars.size());
        assertEquals("Audi", cars.get(0).getBrand());
        assertEquals("BMW", cars.get(1).getBrand());
    }

    @Test
    void testFindCarById() {
        Mockito.when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car1));
        CarResponse carResult = carService.findCarById(1L, false);

        assertEquals(1, carResult.getId());
        assertEquals("Audi", carResult.getBrand());
        assertEquals("A4", carResult.getModel());
        assertEquals(100000, carResult.getPricePrDay());
        assertEquals(10, carResult.getBestDiscount());

    }

    @Test
    void testAddNewCar() {
        CarRequest carRequest = CarRequest.builder().brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        Car newCar = CarRequest.getCarEntity(carRequest);
        newCar.setId(1L);
        newCar.setCreated(LocalDateTime.now());

        Mockito.when(carRepository.save(newCar)).thenReturn(newCar);

        CarResponse carResult = carService.addNewCar(carRequest);
        assertNotNull(carResult);
        assertEquals(1, carResult.getId());
        assertEquals("Audi", carResult.getBrand());
        assertEquals("A4", carResult.getModel());
        assertEquals(100000, carResult.getPricePrDay());
        assertEquals(10, carResult.getBestDiscount());
    }

    @Test
    void testUpdateCar() {

        CarRequest carUpdateRequest = CarRequest.builder().brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();

        Mockito.when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car1));
        Mockito.when(carRepository.save(car1)).thenReturn(car1);

        Car updatedCar = Car.builder().id(car1.getId()).brand("BMW").model("M3").pricePrDay(200000).bestDiscount(20).build();
        CarResponse updatedCarResult = carService.updateCar(carUpdateRequest, 1L);

        assertNotNull(updatedCarResult);
        assertEquals(1, updatedCarResult.getId());
        assertEquals("BMW", updatedCarResult.getBrand());
        assertEquals("M3", updatedCarResult.getModel());
        assertEquals(200000, updatedCarResult.getPricePrDay());
        assertEquals(20, updatedCarResult.getBestDiscount());

    }

    @Test
    void testDeleteCar() {
        Mockito.when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car1));
        carService.deleteCar(1L);
        Mockito.verify(carRepository, Mockito.times(1)).deleteById(1L);
    }
}
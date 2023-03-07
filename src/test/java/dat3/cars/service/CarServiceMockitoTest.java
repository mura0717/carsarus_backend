package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceMockitoTest {

    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarService carService;

    private Car car1;
    private Car car2;


    @BeforeEach
    void setUp() {

        car1 = Car.builder().id(1L).brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        car2 = Car.builder().id(2L).brand("BMW").model("M3").pricePrDay(200000).bestDiscount(20).build();
        car1.setCreated(LocalDateTime.now());
        car2.setCreated(LocalDateTime.now());
        carService = new CarService(carRepository);
    }

    @Test
    void testGetCars() {
        when(carRepository.findAll()).thenReturn(List.of(car1, car2));

        List<CarResponse> carsAdmin = carService.getCars(true);
        assertEquals(2, carsAdmin.size());
        assertNotNull(carsAdmin.get(0).getCreated());

        List<CarResponse> carsUser = carService.getCars(false);
        assertEquals(2, carsUser.size());
        assertNull(carsUser.get(0).getCreated());
    }

    @Test
    void testFindCarById() {
        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car1));
        CarResponse carResult = carService.findCarById(1L, true);

        assertEquals(1L, carResult.getId());
        assertEquals("Audi", carResult.getBrand());
        assertEquals("A4", carResult.getModel());
        assertEquals(100000, carResult.getPricePrDay());
        assertEquals(10, carResult.getBestDiscount());

    }

    @Test
    void testAddNewCar() {
        CarRequest carRequest = CarRequest.builder().brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        Car newCar = CarRequest.carFromCarRequest(carRequest);
        newCar.setId(1);
        newCar.setCreated(LocalDateTime.now());

        when(carRepository.save(any(Car.class))).thenReturn(newCar);

        CarResponse carResult = carService.addNewCar(carRequest);
        assertNotNull(carResult);
        assertEquals(1, carResult.getId());
        assertEquals("Audi", carResult.getBrand());
        assertEquals("A4", carResult.getModel());
        assertEquals(100000, carResult.getPricePrDay());
        assertEquals(10, carResult.getBestDiscount());
    }

    @Test
    void testEditCar() {

        CarRequest carEditRequest = CarRequest.builder().brand("BMW").model("3 Series").pricePrDay(120000).bestDiscount(15).build();
        Car editedCar = Car.builder().id(car1.getId()).brand("Audi").model("A4").pricePrDay(car1.getPricePrDay()).bestDiscount(car1.getBestDiscount()).build();

        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car1));
        when(carRepository.save(any(Car.class))).thenReturn(editedCar);

        CarResponse editedCarResponse = carService.editCar(carEditRequest, 1);
        assertEquals(1, editedCarResponse.getId());
        assertEquals("Audi", editedCarResponse.getBrand());
        assertEquals("A4", editedCarResponse.getModel());

    }


    @Test
    void testDeleteCar() {
        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car1));
        carService.deleteCar(1L);
        Mockito.verify(carRepository, Mockito.times(1)).delete(car1);
    }
}
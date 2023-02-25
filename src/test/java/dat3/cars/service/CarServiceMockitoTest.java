package dat3.cars.service;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        Mockito.when(carRepository.findAll()).thenReturn(dummyCars);
        List<CarResponse> cars = carService.getAllCars(false);

        assertEquals(2, cars.size());
        assertEquals("Audi", cars.get(0).getBrand());
        assertEquals("BMW", cars.get(1).getBrand());
    }

    @Test
    void findCarById() {
        Mockito.when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car1));
        CarResponse carResult = carService.findCarById(1L, false);

        assertEquals(1, carResult.getId());
        assertEquals("Audi", carResult.getBrand());
        assertEquals("A4", carResult.getModel());
        assertEquals(100000, carResult.getPricePrDay());
        assertEquals(10, carResult.getBestDiscount());

    }

    @Test
    void addNewCar() {

    }

    @Test
    void updateCar() {
    }

    @Test
    void deleteCar() {
    }
}
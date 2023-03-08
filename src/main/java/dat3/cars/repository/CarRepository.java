package dat3.cars.repository;

import dat3.cars.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.LongAccumulator;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByBrandAndModel(String brand, String model);

    @Query(value = "SELECT AVG(c.pricePrDay) from Car c")
    Double avrPricePrDay();

    @Query("SELECT c FROM Car c WHERE c.bestDiscount = (SELECT MAX(c.bestDiscount) FROM Car c)")
    List<Car> findAllByBestDiscount();

    List<Car> findByReservationsIsEmpty();
}

package dat3.cars.dto;

import dat3.cars.entity.Car;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRequest {
    long id;
    String brand;
    String model;
    double pricePrDay;
    int bestDiscount;

    public static Car carFromCarRequest(CarRequest car){
        return Car.builder().brand(car.brand).model(car.model).pricePrDay(car.pricePrDay).bestDiscount(car.bestDiscount).build();
    }//How does the id generated? Auto?

}

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

    public static Car getCarEntity(CarRequest car){
        return Car.builder().brand(car.brand).model(car.model).pricePrDay(car.pricePrDay).bestDiscount(car.bestDiscount).build();
    }

/*    //Car to CarRequest Conversion
    public CarRequest(Car car){
        this.id = car.getId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.pricePrDay =car.getPricePrDay();
        this.bestDiscount = car.getBestDiscount();
    }*/
}

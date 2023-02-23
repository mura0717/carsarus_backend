package dat3.cars.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import dat3.cars.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarResponse {
    long id;
    String brand;
    String model;
    double pricePrDay;
    int bestDiscount;

    //Entity to DTO Conversion constructor
    public CarResponse(Car car){
        this.id = car.getId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.pricePrDay = car.getPricePrDay();
        this.bestDiscount = car.getBestDiscount();
    }

}

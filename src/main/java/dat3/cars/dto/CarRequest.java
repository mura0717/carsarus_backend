package dat3.cars.dto;

import dat3.cars.entity.Car;

public class CarRequest {
    long id;
    String brand;
    String model;
    double pricePrDay;
    int bestDiscount;

    public static Car getCarEntity(CarRequest car){
        return new Car(car.id, car.brand, car.model, car.pricePrDay, car.bestDiscount);
    }

    //Car to CarRequest Conversion
    CarRequest(Car car){
        this.id = car.getId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.pricePrDay =car.getPricePrDay();
        this.bestDiscount = car.getBestDiscount();
    }
}

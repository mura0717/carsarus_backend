package dat3.cars.entity;

import jakarta.persistence.*;

@Entity
public class Car {
    @Column(name = "car_id", nullable = false, length = 50)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name= "car_brand", nullable = false, length = 50)
    private String brand;
    @Column(name= "model", nullable = false, length = 60)
    private String model;
    @Column(name= "rental_price_day")
    private double pricePrDay;
    @Column(name= "max_discount")
    private int bestDiscount;

}

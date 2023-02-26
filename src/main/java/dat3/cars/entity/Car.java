package dat3.cars.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
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

    @CreationTimestamp
    LocalDateTime created;

    @UpdateTimestamp
    LocalDateTime updated;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Reservation> reservations;

}

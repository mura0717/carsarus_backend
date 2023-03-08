package dat3.cars.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @CreationTimestamp
    LocalDate reservationDate;

    LocalDate rentalStartDate;
    //LocalDateTime rentalEndDate;

    @ManyToOne
    Member member;
    @ManyToOne
    Car car;

    public Reservation(Member member, Car car, LocalDate rentalStartDate) {
        this.member = member;
        this.car = car;
        this.rentalStartDate = rentalStartDate;
        //this.rentalEndDate = rentalEndDate;
    }
}

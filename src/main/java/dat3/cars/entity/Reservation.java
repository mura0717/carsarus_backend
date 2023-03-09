package dat3.cars.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    LocalDate rentalDate;
    //LocalDateTime rentalEndDate;

    @ManyToOne
    Member member;
    @ManyToOne
    Car car;

    public Reservation(Member member, Car car, LocalDate rentalDate) {
        this.member = member;
        this.car = car;
        this.rentalDate = rentalDate;
        //this.rentalEndDate = rentalEndDate;
    }
}

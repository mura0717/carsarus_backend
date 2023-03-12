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
    private int id;
    @CreationTimestamp
    private LocalDate reservationDate; //The date the reservation was made.
    private LocalDate rentalDate; //The car's rental date

    @ManyToOne
    Member member;
    @ManyToOne
    Car car;

    public Reservation(int id, Member member, Car car, LocalDate rentalDate) {
        this.id = id;
        this.member = member;
        this.car = car;
        this.rentalDate = rentalDate;
    }
}

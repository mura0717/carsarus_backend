package dat3.cars.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @CreationTimestamp
    LocalDateTime reservationDate;

    LocalDateTime rentalStartDate;
    LocalDateTime rentalEndDate;

    @ManyToOne
    Member member;
    @ManyToOne
    Car car;

    public Reservation(LocalDateTime rentalStartDate, LocalDateTime rentalEndDate, Member member) {
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.member = member;
    }
}

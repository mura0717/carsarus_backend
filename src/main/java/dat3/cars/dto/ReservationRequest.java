package dat3.cars.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequest {

    private long carId;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateToReserveCar;


/*    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    LocalDate reservationDate;
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    LocalDate rentalStartDate;
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    LocalDate rentalEndDate;
    Member member;
    Car car;

    public ReservationRequest(Reservation r){
        this.id = r.getId();
        this.reservationDate = r.getReservationDate();
        this.rentalStartDate = r.getRentalStartDate();
        //this.rentalEndDate = r.getRentalEndDate();
        this.member = r.getMember();
        this.car = r.getCar();
    }

 */
}

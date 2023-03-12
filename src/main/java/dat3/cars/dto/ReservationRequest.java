package dat3.cars.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private int id;
    private Member member;
    private Car car;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate rentalDate;

  private static Reservation getReservationEntity (ReservationRequest r){
      return new Reservation(r.getId(), r.getMember(), r.getCar(), r.getRentalDate());
  }

    public ReservationRequest(Reservation r){
        this.id = r.getId();
        this.member = r.getMember();
        this.car = r.getCar();
        this.rentalDate = r.getRentalDate();
    }

}

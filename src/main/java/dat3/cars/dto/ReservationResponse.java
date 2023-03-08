package dat3.cars.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {
    int id;
    String memberUsername;
    long carId;
    double pricePrDay;
    String carBrand;
    String carModel;

    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    private LocalDate rentalDate;

    public ReservationResponse(Reservation r) {
        this.id= r.getId();
        this.memberUsername = r.getMember().getUsername();
        this.carId = r.getCar().getId();
        this.carBrand = r.getCar().getBrand();
        this.carModel = r.getCar().getModel();
        this.pricePrDay = r.getCar().getPricePrDay();
        this.rentalDate = r.getRentalStartDate();
    }


    /*long id;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDate reservationDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDate rentalStartDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDate rentalEndDate;
    Member member;
    Car car;

public ReservationResponse(Reservation r) {
        this.id = r.getId();
        this.reservationDate = r.getReservationDate();
        this.rentalStartDate = r.getRentalStartDate();
        //this.rentalEndDate = r.getRentalEndDate();
        this.member = r.getMember();
        this.car = r.getCar();
    }

     */

}

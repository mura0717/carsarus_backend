package dat3.cars.service;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;


//@Transactional
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final MemberRepository memberRepository;
    private final CarRepository carRepository;

    public ReservationService(ReservationRepository reservationRepository, MemberRepository memberRepository, CarRepository carRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.carRepository = carRepository;
    }

    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> reservationResponses = reservations.stream().map((r) -> new ReservationResponse(r)).toList();
        return reservationResponses;
    }

    public ReservationResponse findReservationById(int id){
        Reservation found = reservationRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reservation not found."));
        return new ReservationResponse(found);
    }

    public ReservationResponse makeReservation (ReservationRequest reservationRequest){
        if (reservationRepository.existsByCarIdAndRentalDate(reservationRequest.getCar().getId(), reservationRequest.getRentalDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car is already reserved on this day");
        }

        Car car = carRepository.findById(reservationRequest.getCar().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car with provided id not found"));
        Member member = memberRepository.findById(reservationRequest.getMember().getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member with provided username not found"));

        // Check if the reservation date is in the future
        if (reservationRequest.getRentalDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation date cannot be a date in the past");
        }
        Reservation newReservation = new Reservation();
            newReservation.setId(reservationRequest.getId());
            newReservation.setMember(member);
            newReservation.setCar(car);
            newReservation.setRentalDate(reservationRequest.getRentalDate());
        newReservation = reservationRepository.save(newReservation);
        return new ReservationResponse(newReservation);
    }

    public List<ReservationResponse> getReservationsForUser(String username) {
        Member member = memberRepository.findById(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        List<ReservationResponse> reservations = member.getReservations().stream().map(r -> new ReservationResponse(r)).toList();
        return reservations;
    }

    public void deleteReservation(int id){
        Reservation found = reservationRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reservation not found."));
        reservationRepository.delete(found);
    }
}

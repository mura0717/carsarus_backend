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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;


//@Transactional

@Service
public class ReservationService {

    ReservationRepository reservationRepository;
//    ReservationRequest reservationRequest
//    ReservationResponse reservationResponse;

    MemberRepository memberRepository;
    CarRepository carRepository;

/*    public ReservationService (ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }*/

    public ReservationService(ReservationRepository reservationRepository, MemberRepository memberRepository, CarRepository carRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.carRepository = carRepository;
    }

    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> reservationResponses = reservations.stream().map((r) -> new ReservationResponse()).toList();
        return reservationResponses;
    }

    public ReservationResponse findReservationById(int id){
        Reservation found = reservationRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reservation not found."));
        return new ReservationResponse(found);
    }

    public ReservationResponse makeReservation (ReservationRequest body){
        if (reservationRepository.existsByCarIdAndRentalDate(body.getCarId(), body.getDateToReserveCar())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car is already reserved on this day");
        }
        Car car = carRepository.findById(body.getCarId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car with provide id not found"));
        Member member = memberRepository.findById(body.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member with provided username not found"));

        // Check if the reservation date is in the future
        if (body.getDateToReserveCar().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation date cannot be a date in the past");
        }
        Reservation reservation = new Reservation(member, car, body.getDateToReserveCar());
        reservation = reservationRepository.save(reservation);
        return new ReservationResponse(reservation);
    }

    public List<ReservationResponse> getReservationsForUser(String username) {
        Member member = memberRepository.findById(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        List<ReservationResponse> reservations = member.getReservations().stream().map(r -> new ReservationResponse(r)).toList();
        return reservations;
    }

/*    public ReservationResponse createNewReservation(ReservationRequest reservationRequest){
        //Check if car is available
        long car = reservationRequest.getCarId();
        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(car, reservationRequest.getRentalStartDate(), reservationRequest.getRentalEndDate());
        if(!conflictingReservations.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Car is not available for the requested rental period.");
        }

        Reservation reservation = reservationRepository.save(new Reservation(reservationRequest.getMember(), reservationRequest.getCar(), reservationRequest.getRentalStartDate()));
        return new ReservationResponse(reservation);
    }

    public ReservationResponse updateReservation (ReservationRequest body, int id){
        Reservation reservationToEdit = reservationRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reservation not found."));
        reservationToEdit.setReservationDate(body.getDateToReserveCar());
        reservationToEdit.setRentalStartDate(body.getDateToReserveCar());
        //reservationToEdit.setRentalEndDate(body.getRentalEndDate());
        reservationToEdit.setMember(body.getMember());
        reservationToEdit.setCar(body.getCar());
        Reservation updatedReservation = reservationRepository.save(reservationToEdit);
        return new ReservationResponse(updatedReservation);
    }*/

    public void deleteReservation(int id){
        Reservation found = reservationRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reservation not found."));
        reservationRepository.delete(found);
    }
}

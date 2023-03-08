package dat3.cars.service;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
@Transactional
public class ReservationService {

    ReservationRepository reservationRepository;
    ReservationRequest reservationRequest;
    ReservationResponse reservationResponse;


    MemberRepository memberRepository;
    MemberRequest memberRequest;

    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
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

    public ReservationResponse createNewReservation(ReservationRequest reservationRequest){
        //Check if car is available
        Car car = reservationRequest.getCar();
        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(car, reservationRequest.getRentalStartDate(), reservationRequest.getRentalEndDate());
        if(!conflictingReservations.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Car is not available for the requested rental period.");
        }

        Reservation reservation = reservationRepository.save(new Reservation(reservationRequest.getMember(), reservationRequest.getCar(), reservationRequest.getRentalStartDate()));
        return new ReservationResponse(reservation);
    }

    public ReservationResponse updateReservation (ReservationRequest body, int id){
        Reservation reservationToEdit = reservationRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reservation not found."));
        reservationToEdit.setReservationDate(body.getReservationDate());
        reservationToEdit.setRentalStartDate(body.getRentalStartDate());
        //reservationToEdit.setRentalEndDate(body.getRentalEndDate());
        reservationToEdit.setMember(body.getMember());
        reservationToEdit.setCar(body.getCar());
        Reservation updatedReservation = reservationRepository.save(reservationToEdit);
        return new ReservationResponse(updatedReservation);
    }

    public void deleteReservation(int id){
        Reservation found = reservationRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reservation not found."));
        reservationRepository.delete(found);
    }
}

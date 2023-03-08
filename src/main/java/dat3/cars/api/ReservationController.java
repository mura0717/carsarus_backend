package dat3.cars.api;

import dat3.cars.dto.MemberResponse;
import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.service.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {

    ReservationService reservationService;

    @GetMapping
    List<ReservationResponse> getReservations(){
        return reservationService.getAllReservations();
    }

    @GetMapping(path = "/{id}")
    ReservationResponse getReservationById(@PathVariable int id) {
        return reservationService.findReservationById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ReservationResponse addReservation(@RequestBody ReservationRequest body){
        return reservationService.makeReservation(body);
    }

/*    @PutMapping(path = "/{id}")
    ReservationResponse editReservationById(@RequestBody ReservationRequest body, @PathVariable int id) {
        return reservationService.updateReservation(body, id);
    }*/

    @DeleteMapping(path = "/{id}")
    void deleteReservationById(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }

}

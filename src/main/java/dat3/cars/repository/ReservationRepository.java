package dat3.cars.repository;

import dat3.cars.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    boolean existsByCarIdAndRentalDate(long carId, LocalDate rentalDate);
    List<Reservation> findByMemberUsername(String userName);
    Long countReservationsByMemberUsername(String username);
}

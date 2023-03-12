package dat3.cars.service;

import dat3.cars.dto.MemberResponse;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReservationServiceH2Test {

    ReservationService reservationService;

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    MemberRepository memberRepository;

    private Car car1;
    private Car car2;
    private Member m1;
    private Member m2;
    private Reservation res1;
    private Reservation res2;

    boolean dataIsInitialized = true;

    @BeforeEach
    void setUp() {
        if(!dataIsInitialized) return;

        reservationRepository.deleteAll();
        memberRepository.deleteAll();
        carRepository.deleteAll();
        car1 = Car.builder().id(1).brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        car2 = Car.builder().id(2).brand("BMW").model("M3").pricePrDay(200000).bestDiscount(30).build();
        carRepository.saveAndFlush(car1);
        carRepository.saveAndFlush(car2);
        m1 = memberRepository.saveAndFlush(new Member("m1", "test12", "m1@a.dk", "Bibi", "Olsen", "xx vej 34", "Lyngby", "2800"));
        m2 = memberRepository.saveAndFlush(new Member("m2", "test12", "m2@a.dk", "Martin", "Hansen", "yy vej 34", "Amager", "4000"));
        res1 = reservationRepository.saveAndFlush(new Reservation(1, m1, car1, LocalDate.parse("2023-04-04")));
        res2 = reservationRepository.saveAndFlush(new Reservation(2, m2, car2, LocalDate.parse("2023-05-04")));

        reservationService = new ReservationService(reservationRepository, memberRepository, carRepository);
        dataIsInitialized = true;
    }

    @Test
    void testGetReservations() {
        List<ReservationResponse> reservationResponseList = reservationService.getReservations();
        assertEquals(2, reservationResponseList.size());
        System.out.println(reservationResponseList);
        assertEquals(1, reservationResponseList.get(0).getCarId());
        assertEquals("Audi", reservationResponseList.get(0).getCarBrand());
        assertEquals("m2", reservationResponseList.get(1).getMemberUsername());
        assertEquals(LocalDate.parse("2023-05-04"), reservationResponseList.get(1).getRentalDate());
    }

    @Test
    void findReservationById() {

        ReservationResponse foundReservation = reservationService.findReservationById(res1.getId());
        assertNotNull(foundReservation);
        assertEquals(1, foundReservation.getId());
        assertEquals("m1", foundReservation.getMemberUsername());
        assertEquals(1, foundReservation.getCarId());
        assertEquals("Audi", foundReservation.getCarBrand());
        assertEquals(LocalDate.parse("2023-04-04"), foundReservation.getRentalDate());
    }

    @Test
    void makeReservation() {
    }

    @Test
    void getReservationsForUser() {
    }

    @Test
    void deleteReservation() {
        reservationRepository.deleteById(res1.getId());
        Optional<Reservation> retrievedReservation = reservationRepository.findById(res1.getId());
        assertFalse(retrievedReservation.isPresent());
    }
}
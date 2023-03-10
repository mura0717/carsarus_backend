package dat3.cars.service;

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
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ReservationServiceTest {

    ReservationService reservationService;

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    MemberRepository memberRepository;

    boolean dataIsInitialized = false;

    @BeforeEach
    void setUp() {
        if(!dataIsInitialized) return;
        Car car1 = Car.builder().id(1).brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        carRepository.saveAndFlush(car1);
        Member m1 = memberRepository.saveAndFlush(new Member("m1", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800"));
        Reservation res1 = reservationRepository.saveAndFlush(new Reservation(m1, car1, LocalDate.parse("2023-04-04")));
        dataIsInitialized = true;
    }

    @Test
    void getAllReservations() {
    }

    @Test
    void findReservationById() {
    }

    @Test
    void makeReservation() {
    }

    @Test
    void getReservationsForUser() {
    }

    @Test
    void deleteReservation() {
    }
}
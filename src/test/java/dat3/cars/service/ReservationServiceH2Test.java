package dat3.cars.service;

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

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ReservationServiceH2Test {

    @Autowired
    ReservationRepository reservationRepository;
    ReservationService reservationService;

    @Autowired
    CarRepository carRepository;
    @Autowired
    MemberRepository memberRepository;

    boolean dataIsInitialized = false;

    @BeforeEach
    void setUp() {
        if(!dataIsInitialized) return;
        Car car1 = Car.builder().id(1).brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        Car car2 = Car.builder().id(2).brand("BMW").model("M3").pricePrDay(200000).bestDiscount(30).build();
        carRepository.saveAndFlush(car1);
        carRepository.saveAndFlush(car2);
        Member m1 = memberRepository.saveAndFlush(new Member("m1", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800"));
        Member m2 = memberRepository.saveAndFlush(new Member("m2", "test12", "m2@a.dk", "MArtin", "Hansen", "yy vej 34", "Amager", "4000"));
        Reservation res1 = reservationRepository.saveAndFlush(new Reservation(m1, car1, LocalDate.parse("2023-04-04")));
        Reservation res2 = reservationRepository.saveAndFlush(new Reservation(m2, car2, LocalDate.parse("2023-05-04")));
        reservationRepository.saveAndFlush(res1);
        reservationRepository.saveAndFlush(res2);

        reservationService = new ReservationService(reservationRepository, memberRepository, carRepository);
        dataIsInitialized = true;
    }

    @Test
    void testGetAllReservations() {
        List<ReservationResponse> reservationResponseList = reservationService.getAllReservations();
        assertEquals(2, reservationResponseList.size());
        assertEquals(1, reservationResponseList.get(0).getCarId());
        assertEquals("Audi", reservationResponseList.get(0).getCarBrand());
        assertEquals("m2", reservationResponseList.get(1).getMemberUsername());
        assertEquals(LocalDate.parse("2023-05-04"), reservationResponseList.get(1).getRentalDate());
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
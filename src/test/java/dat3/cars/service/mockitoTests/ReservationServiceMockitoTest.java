package dat3.cars.service.mockitoTests;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import dat3.cars.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceMockitoTest {

    @InjectMocks
    ReservationService reservationService;

/*    @InjectMocks
    CarService carService;

    @InjectMocks
    MemberService memberService;*/

    @Mock
    ReservationRepository reservationRepository;
    @Mock
    CarRepository carRepository;
    @Mock
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

        car1 = Car.builder().brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        car2 = Car.builder().brand("BMW").model("M3").pricePrDay(200000).bestDiscount(30).build();
        m1 = new Member("m1", "test12", "m1@a.dk", "Bibi", "Olsen", "xx vej 34", "Lyngby", "2800");
        m2 = new Member("m2", "test12", "m2@a.dk", "Martin", "Hansen", "yy vej 34", "Amager", "4000");
        res1 = new Reservation(1, m1, car1, LocalDate.parse("2023-04-04"));//Reservation id is overwritten by the database once all tests are run.
        res2 = new Reservation(2, m2, car2, LocalDate.parse("2023-05-04"));//Reservation id is overwritten by the database once all tests are run.

        carRepository.save(car1);
        carRepository.save(car2);

        // cars and members are not saved in the repository, but reservations are???
/*        carRepository.saveAndFlush(car1);
        carRepository.saveAndFlush(car2);
        memberRepository.saveAndFlush(m1);
        memberRepository.saveAndFlush(m2);
        reservationRepository.saveAndFlush(res1);
        reservationRepository.saveAndFlush(res2);*/

/*        car1.setCreated(LocalDateTime.now());
        car2.setCreated(LocalDateTime.now());
        m1.setCreated(LocalDateTime.now());
        m2.setCreated(LocalDateTime.now());

        when(carRepository.save(any(Car.class))).thenReturn(car1);
        when(carRepository.save(any(Car.class))).thenReturn(car2);
        when(memberRepository.save(any(Member.class))).thenReturn(m1);
        when(memberRepository.save(any(Member.class))).thenReturn(m2);*/

        reservationService = new ReservationService(reservationRepository, memberRepository, carRepository);
//        carService = new CarService(carRepository);
//        memberService = new MemberService(memberRepository);

        dataIsInitialized = true;
    }



    @Test
    void testGetReservations() {
        when(reservationRepository.findAll()).thenReturn(List.of(res1, res2));
        List<ReservationResponse> reservationResponseList = reservationService.getReservations();
        assertEquals(2, reservationResponseList.size());
        assertEquals(0, reservationResponseList.get(0).getCarId());
        assertEquals("Audi", reservationResponseList.get(0).getCarBrand());
        assertEquals("m2", reservationResponseList.get(1).getMemberUsername());
        assertEquals(LocalDate.parse("2023-05-04"), reservationResponseList.get(1).getRentalDate());
    }

    @Test
    void findReservationById() {
        when(reservationRepository.findById(1)).thenReturn(java.util.Optional.of(res1));
        ReservationResponse foundReservation = reservationService.findReservationById(res1.getId());
        assertNotNull(foundReservation);
        assertEquals(1, foundReservation.getId());
        assertEquals("m1", foundReservation.getMemberUsername());
        assertEquals(1, foundReservation.getCarId());
        assertEquals("Audi", foundReservation.getCarBrand());
        assertEquals(LocalDate.parse("2023-04-04"), foundReservation.getRentalDate());
    }

    @Test
    void getReservationsForUser() {

        // Arrange
        m1.setReservations(List.of(res1, res2));
        when(memberRepository.findById("m1")).thenReturn(Optional.of(m1));

        // Act
        List<ReservationResponse> reservationResponseList = reservationService.getReservationsForUser("m1");

        // Assert
        assertEquals(2, reservationResponseList.size());
        assertEquals(1, reservationResponseList.get(0).getCarId());
        assertEquals("Audi", reservationResponseList.get(0).getCarBrand());
        assertEquals("m2", reservationResponseList.get(1).getMemberUsername());
        assertEquals(LocalDate.parse("2023-05-04"), reservationResponseList.get(1).getRentalDate());


    }

    @Test
    void makeReservation() {

        // Arrange
        Car car3 = Car.builder().id(3).brand("Volvo").model("S80").pricePrDay(600).bestDiscount(12).build();
        when(carRepository.findById(eq(car3.getId()))).thenReturn(Optional.of(car3));

        Member m3 = new Member("m3", "test12", "m3@a.dk", "Peter", "Lind", "Lindevej vej 12", "Østerbro", "2400");
        when(memberRepository.findById(eq(m3.getUsername()))).thenReturn(Optional.of(m3));

        Reservation res3 = new Reservation(3, m3, car3, LocalDate.parse("2023-06-01"));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(res3);

        ReservationRequest reservationRequest = new ReservationRequest(res3);

        // Act
        ReservationResponse reservationResponse = reservationService.makeReservation(reservationRequest);

        // Assert
        assertNotNull(reservationResponse);
        assertEquals(3, reservationResponse.getId());
        assertEquals("m3", reservationResponse.getMemberUsername());
        assertEquals(3, reservationResponse.getCarId());
        assertEquals("Volvo", reservationResponse.getCarBrand());
        assertEquals(LocalDate.parse("2023-06-01"), reservationResponse.getRentalDate());
    }

    @Test
    void deleteReservation() {
        reservationRepository.deleteById(res1.getId());
        Optional<Reservation> retrievedReservation = reservationRepository.findById(res1.getId());
        assertFalse(retrievedReservation.isPresent());
    }
}

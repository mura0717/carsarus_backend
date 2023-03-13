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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceMockitoTest {

    @InjectMocks
    ReservationService reservationService;

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

        reservationRepository = mock(ReservationRepository.class);
        carRepository = mock(CarRepository.class);
        memberRepository = mock(MemberRepository.class);

        car1 = Car.builder().brand("Audi").model("A4").pricePrDay(100000).bestDiscount(10).build();
        car2 = Car.builder().brand("BMW").model("M3").pricePrDay(200000).bestDiscount(30).build();
        m1 = new Member("m1", "test12", "m1@a.dk", "Bibi", "Olsen", "xx vej 34", "Lyngby", "2800");
        m2 = new Member("m2", "test12", "m2@a.dk", "Martin", "Hansen", "yy vej 34", "Amager", "4000");
        res1 = new Reservation(1, m1, car1, LocalDate.parse("2023-04-04"));//Reservation id is overwritten by the database once all tests are run.
        res2 = new Reservation(2, m2, car2, LocalDate.parse("2023-05-04"));//Reservation id is overwritten by the database once all tests are run.

        /*
        Apparently unncessary, as the when() method is already used in the test method.

        when(carRepository.saveAndFlush(any())).thenReturn(car1).thenReturn(car2);
        when(memberRepository.saveAndFlush(any())).thenReturn(m1).thenReturn(m2);
        when(reservationRepository.saveAndFlush(any())).thenReturn(res1).thenReturn(res2);

        */

        reservationService = new ReservationService(reservationRepository, memberRepository, carRepository);
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

}

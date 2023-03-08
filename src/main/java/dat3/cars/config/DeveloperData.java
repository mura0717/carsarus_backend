package dat3.cars.config;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;

import dat3.cars.repository.ReservationRepository;
import dat3.security.entity.Role;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

//@Controller
//@Configuration
//@EnableJpaRepositories(basePackages = {"dat3.cars.repository", "dat3.security.repository"})
//@ComponentScan(basePackages = {"dat3.security.repository"})

@Component
public class DeveloperData implements CommandLineRunner {

    CarRepository carRepository;
    MemberRepository memberRepository;
    ReservationRepository reservationRepository;

    public DeveloperData(CarRepository carRepository,MemberRepository memberRepository, ReservationRepository reservationRepository) {
        this.memberRepository = memberRepository;
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    void testData() {
        String passwordUsedByAll = "test12";

        Member m1 = new Member("member1", passwordUsedByAll, "member4@a.dk", "Esra", "Babacan", "Lyngbyvej 6", "Lyngby", "2800");
        Member m2 = new Member("member2", passwordUsedByAll, "member5@a.dk", "Ronja", "Auster", "Lyngbyvej 8", "Lyngby", "2800");
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(new Member("member3", passwordUsedByAll, "member1@a.dk", "Kurt", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800"));
        memberRepository.save(new Member("member4", passwordUsedByAll, "member2@a.dk", "Hanne", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800"));
        memberRepository.save(new Member("member5", passwordUsedByAll, "member3@a.dk", "John", "T", "Lyngbyvej 4", "Lyngby", "2800"));

        Car car1 = Car.builder().brand("Volvo").model("V70").pricePrDay(500).bestDiscount(10).build();
        carRepository.save(car1);
        createCars();

        //Create a default reservation
        Reservation res1 = new Reservation(m1, car1, LocalDate.of(2023, 12, 12));
        reservationRepository.save(res1);

//        m1.getPhones().put("mobile", "12345");
//        m1.getPhones().put("work", "45678");
//        m2.getFavoriteCarColors().add("red");
//        memberRepository.save(m1);
//        memberRepository.save(m2);

    }

    private void createCars(){
        List<Car> newCars = new ArrayList<>(Arrays.asList(
                Car.builder().brand("Volvo").model("V70").pricePrDay(500).bestDiscount(10).build(),
                Car.builder().brand("Suzuki").model("Swift").pricePrDay(350).bestDiscount(6).build(),
                Car.builder().brand("Kia").model("Optima").pricePrDay(450).bestDiscount(18).build(),
                Car.builder().brand("WW").model("Wagon").pricePrDay(400).bestDiscount(20).build(),
                Car.builder().brand("Volvo").model("S80").pricePrDay(600).bestDiscount(12).build(),
                Car.builder().brand("Suzuki").model("SX4").pricePrDay(400).bestDiscount(16).build(),
                Car.builder().brand("Suzuki").model("SX4").pricePrDay(400).bestDiscount(16).build(),
                Car.builder().brand("Suzuki").model("SX4").pricePrDay(400).bestDiscount(16).build(),
                Car.builder().brand("Kia").model("Sorento").pricePrDay(500).bestDiscount(22).build(),
                Car.builder().brand("WW").model("Pickup").pricePrDay(450).bestDiscount(28).build(),
                Car.builder().brand("Volvo").model("V60").pricePrDay(700).bestDiscount(15).build(),
                Car.builder().brand("Suzuki").model("Grand Vitara").pricePrDay(450).bestDiscount(12).build(),
                Car.builder().brand("Kia").model("Sportage").pricePrDay(500).bestDiscount(20).build(),
                Car.builder().brand("WW").model("SUV").pricePrDay(400).bestDiscount(18).build(),
                Car.builder().brand("Volvo").model("XC90").pricePrDay(800).bestDiscount(25).build(),
                Car.builder().brand("Volvo").model("XC90").pricePrDay(800).bestDiscount(25).build(),
                Car.builder().brand("Volvo").model("XC90").pricePrDay(800).bestDiscount(25).build(),
                Car.builder().brand("Suzuki").model("Baleno").pricePrDay(450).bestDiscount(15).build(),
                Car.builder().brand("Kia").model("Stinger").pricePrDay(600).bestDiscount(12).build(),
                Car.builder().brand("WW").model("Sedan").pricePrDay(400).bestDiscount(20).build(),
                Car.builder().brand("Volvo").model("XC40").pricePrDay(700).bestDiscount(30).build(),
                Car.builder().brand("Volvo").model("XC40").pricePrDay(700).bestDiscount(30).build(),
                Car.builder().brand("Volvo").model("XC40").pricePrDay(700).bestDiscount(30).build(),
                Car.builder().brand("Suzuki").model("Ignis").pricePrDay(400).bestDiscount(14).build(),
                Car.builder().brand("Kia").model("Rio").pricePrDay(450).bestDiscount(12).build(),
                Car.builder().brand("WW").model("Hatchback").pricePrDay(450).bestDiscount(16).build()
        ));
        carRepository.saveAll(newCars);
    }

    @Autowired
    UserWithRolesRepository userWithRolesRepository;
    final String passwordUsedByAll = "test12";

    private void setupUserWithRoleUsers() {

        System.out.println("******************************************************************************");
        System.out.println("******* NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL ************");
        System.out.println("******* REMOVE THIS BEFORE DEPLOYMENT, AND SETUP DEFAULT USERS DIRECTLY  *****");
        System.out.println("**** ** ON YOUR REMOTE DATABASE                 ******************************");
        System.out.println("******************************************************************************");
        UserWithRoles user1 = new UserWithRoles("user1", passwordUsedByAll, "user1@a.dk");
        UserWithRoles user2 = new UserWithRoles("user2", passwordUsedByAll, "user2@a.dk");
        UserWithRoles user3 = new UserWithRoles("user3", passwordUsedByAll, "user3@a.dk");
        UserWithRoles user4 = new UserWithRoles("user4", passwordUsedByAll, "user4@a.dk");
        user1.addRole(Role.USER);
        user1.addRole(Role.ADMIN);
        user2.addRole(Role.USER);
        user3.addRole(Role.ADMIN);
        //No Role assigned to user4
        userWithRolesRepository.save(user1);
        userWithRolesRepository.save(user2);
        userWithRolesRepository.save(user3);
        userWithRolesRepository.save(user4);
    }

    @Override
    public void run(String... args) throws Exception {
        testData();
        setupUserWithRoleUsers();
    }
}


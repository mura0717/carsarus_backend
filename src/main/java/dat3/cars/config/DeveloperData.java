package dat3.cars.config;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class DeveloperData implements CommandLineRunner {

    MemberRepository memberRepository;
    CarRepository carRepository;

    public DeveloperData(MemberRepository memberRepository, CarRepository carRepository) {
        this.memberRepository = memberRepository;
        this.carRepository = carRepository;
    }


    void makeTestData() {
        final String passwordUsedByAll = "test12";
        Member m1 = new Member("member1", passwordUsedByAll, "memb1@a.dk", "Kurt", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800");
        Member m2 = new Member("member2", passwordUsedByAll, "aaa@dd.dk", "Hanne", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800");
        memberRepository.save(m1);
        memberRepository.save(m2);

        m1.getPhones().put("mobile", "12345");
        m1.getPhones().put("work", "45678");
        m2.getFavoriteCarColors().add("red");
        memberRepository.save(m1);
        memberRepository.save(m2);

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


    @Override
    public void run(String... args) throws Exception {
        makeTestData();
    }
}


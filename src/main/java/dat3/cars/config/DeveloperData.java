package dat3.cars.config;

import dat3.cars.entity.Member;
import dat3.cars.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

@Controller
public class DeveloperData implements ApplicationRunner {

    MemberRepository memberRepository;

    public DeveloperData (MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    private final String passwordUsedByAll = "test12";
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member m1 = new Member("member1", passwordUsedByAll, "memb1@a.dk", "Kurt", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800");
        Member m2 = new Member("member2", passwordUsedByAll, "aaa@dd.dk", "Hanne", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800");
        memberRepository.save(m1);
        memberRepository.save(m2);

        m1.getPhones().put("mobile","12345");
        m1.getPhones().put("work","45678");
        m2.getFavoriteCarColors().add("red");
        memberRepository.save(m1);
        memberRepository.save(m2);
    }

}

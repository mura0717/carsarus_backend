package dat3.cars.service;

import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//Transactional (rollback)
//Includes what is necessary for JPA/Hibernate and ONLY that
//Uses an in-memory database (H2)
@DataJpaTest
class MemberServiceH2Test {

    @Autowired
    public MemberRepository memberRepository;

    MemberService memberService;

    boolean dataIsReady = false;
    @BeforeEach
    void setUp() {
        if(!dataIsReady){  //Explain this
            memberRepository.save(new Member("m1", "test12", "m1@a.dk",  "bb", "Olsen", "xx vej 34", "Lyngby", "2800"));
            memberRepository.save(new Member("m2", "test12", "m2@a.dk", "aa", "hansen", "xx vej 34", "Lyngby", "2800"));
            dataIsReady = true;
            memberService = new MemberService(memberRepository); //Real DB is mocked away with H2
        }
    }

    @Test
    void getMembersAdmin() {
        List<MemberResponse> members = memberService.getMembers(true);
        assertEquals(2,members.size());
        assertNotNull(members.get(0).getCreated());
    }

    @Test
    void addMember() {
        Member newMember = new Member("m3", "test12", "m1@a.dk",  "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
        memberRepository.save(newMember);

        assertNotNull(newMember.getUsername());
        assertEquals("test12", newMember.getPassword());
        assertEquals("m1@a.dk", newMember.getEmail());
        assertEquals("bb", newMember.getFirstName());
        assertEquals("Olsen", newMember.getLastName());
        assertEquals("xx vej 34", newMember.getStreet());
        assertEquals("Lyngby", newMember.getCity());
        assertEquals("2800", newMember.getZip());
    }

    @Test
    void deleteMember() {
        Member newMember = new Member("m3", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
        memberRepository.save(newMember);

        memberRepository.deleteById("m3");
        Optional<Member> retrievedMember = memberRepository.findById("m3");
        assertFalse(retrievedMember.isPresent());
    }
}
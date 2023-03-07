package dat3.cars.service;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Member;
import dat3.cars.repository.MemberRepository;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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

    @PersistenceContext
    private TestEntityManager testEntityManager;

    MemberService memberService;

    static Member m1;
    static Member m2;

    boolean dataIsReady;

    @BeforeEach
    void setUp() throws InterruptedException {
        if (!dataIsReady) return;
        m1 = new Member("m1", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
        m2 = new Member("m2", "test12", "m2@a.dk", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
        m1 = memberRepository.saveAndFlush(m1);
        m2 = memberRepository.saveAndFlush(m2);

        testEntityManager.clear();

        memberService = new MemberService(memberRepository); //Real DB is mocked away with H2
        dataIsReady = true;
    }


    @Test
    void getMembersAdmin() {
        List<MemberResponse> members = memberService.getMembers(true);
        assertEquals(2, members.size());
        assertNotNull(members.get(0).getCreated());
        assertNotNull(members.get(0).getRanking());
    }

    @Test
    void addMember() {
        Member newMember = new Member("m3", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
        memberRepository.save(newMember);

        Optional<Member> retrievedMember = memberRepository.findById(newMember.getUsername());
        assertTrue(retrievedMember.isPresent());
        assertEquals("test12", retrievedMember.get().getPassword());
        assertEquals("m1@a.dk", retrievedMember.get().getEmail());
        assertEquals("bb", retrievedMember.get().getFirstName());
        assertEquals("Olsen", retrievedMember.get().getLastName());
        assertEquals("xx vej 34", retrievedMember.get().getStreet());
        assertEquals("Lyngby", retrievedMember.get().getCity());
        assertEquals("2800", retrievedMember.get().getZip());
    }

    @Test
    void editMember() {
        Member newMember = new Member("m3", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
        memberRepository.save(newMember);
        MemberRequest request = new MemberRequest(newMember);
        MemberResponse response = memberService.editMember(request, newMember.getUsername());
        Member updatedNewMember = memberRepository.findById(newMember.getUsername()).get();
        memberRepository.save(updatedNewMember);

        assertEquals("aa", updatedNewMember.getFirstName());
        assertEquals("Jensen", updatedNewMember.getLastName());
        assertEquals("yy vej 54", updatedNewMember.getStreet());
        assertEquals("København", updatedNewMember.getCity());
        assertEquals("2300", updatedNewMember.getZip());
    }

    @Test
    void deleteMember() {
        Member newMember = new Member("m3", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
        Member savedMember = memberRepository.save(newMember);

        memberRepository.deleteById(savedMember.getUsername());
        Optional<Member> retrievedMember = memberRepository.findById(savedMember.getUsername());
        assertFalse(retrievedMember.isPresent());
    }
}
package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.repository.MemberRepository;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//Transactional (rollback)
//Includes what is necessary for JPA/Hibernate and ONLY that
//Uses an in-memory database (H2)
@DataJpaTest
class MemberServiceH2Test {

    @Autowired
    MemberRepository memberRepository;
    MemberService memberService;

//    @PersistenceContext
//    private TestEntityManager testEntityManager;

    static Member m1;
    static Member m2;

    boolean dataInitialized = true;

    @BeforeEach
    void setUp() {
        if (!dataInitialized) return;
        m1 = new Member("m1", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
        m2 = new Member("m2", "test12", "m2@a.dk", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
        m1.setCreated(LocalDateTime.now());
        m2.setCreated(LocalDateTime.now());
        m1.setApproved(true);
        m2.setApproved(true);
        m1 = memberRepository.saveAndFlush(m1);
        m2 = memberRepository.saveAndFlush(m2);

        memberService = new MemberService(memberRepository);
        dataInitialized = true;
    }


    @Test
    void getMembersAdmin() {
        List<MemberResponse> members = memberService.getMembers(true);
        assertEquals(2, members.size());
        assertNotNull(members.get(0).getApproved());
    }

    @Test
    void getMembersUser() {
        List<MemberResponse> members = memberService.getMembers(false);
        assertEquals(2, members.size());
        assertNull(members.get(0).getCreated());
        assertNull(members.get(0).getRanking());
        assertNull(members.get(0).getApproved());
    }

    @Test
    void testFindMemberByIdAdmin() {
        MemberResponse foundMember = memberService.findMemberByUsername(m1.getUsername(), true);

        assertEquals("m1@a.dk", foundMember.getEmail());
        assertEquals("bb", foundMember.getFirstName());
        assertEquals("Olsen", foundMember.getLastName());
        assertEquals(m1.getCreated(), foundMember.getCreated());
        assertNotNull(foundMember.getApproved());//Why is it null?
    }

    @Test
    void testFindMemberByIdUser() {
        MemberResponse foundMember = memberService.findMemberByUsername(m1.getUsername(), false);

        assertEquals("m1@a.dk", foundMember.getEmail());
        assertEquals("bb", foundMember.getFirstName());
        assertEquals("Olsen", foundMember.getLastName());
        assertNull(foundMember.getCreated());
        assertNull(foundMember.getApproved());
    }

    @Test
    void addMember() {
        Member m3 = new Member("m3", "test12", "m3@a.dk", "Martin", "Hansen", "yy vej 74", "Amager", "4000");
        MemberRequest memberAddRequest = new MemberRequest(m3);
        MemberResponse addedMember = memberService.addMember(memberAddRequest);

        assertDoesNotThrow(() -> memberRepository.findById(addedMember.getUsername()).get());
        assertNotNull(addedMember);
        assertEquals("m3@a.dk", addedMember.getEmail());
        assertEquals("Martin", addedMember.getFirstName());
        assertEquals("Hansen", addedMember.getLastName());
        assertEquals("yy vej 74", addedMember.getStreet());
        assertEquals("Amager", addedMember.getCity());
        assertEquals("4000", addedMember.getZip());
    }

    @Test
    void editMember() {
        Member m3 = new Member("m3", "test12", "m3@a.dk", "Martin", "Hansen", "yy vej 74", "Amager", "4000");
        MemberRequest memberEditRequest = new MemberRequest(m3);
        MemberResponse updatedMember = memberService.editMember(memberEditRequest, m1.getUsername());

        assertEquals("Martin", updatedMember.getFirstName());
        assertEquals("Hansen", updatedMember.getLastName());
        assertEquals("yy vej 74", updatedMember.getStreet());
        assertEquals("Amager", updatedMember.getCity());
        assertEquals("4000", updatedMember.getZip());
    }

    @Test
    void deleteMember() {
        memberRepository.deleteById(m1.getUsername());
        Optional<Member> retrievedMember = memberRepository.findById(m1.getUsername());
        assertFalse(retrievedMember.isPresent());
    }
}
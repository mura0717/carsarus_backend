package dat3.cars.service.mockitoTests;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.MemberRepository;
import dat3.cars.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceMockitoTest {
    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    private Member m1;
    private Member m2;

    @BeforeEach
    void setUp() {
        m1 = new Member("m1", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
        m2 = new Member("m2", "test12", "m2@a.dk", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
        m1.setCreated(LocalDateTime.now());
        m2.setCreated(LocalDateTime.now());
        memberService = new MemberService(memberRepository);
    }


    @Test
    void testGetMembersAdmin() {
        when(memberRepository.findAll()).thenReturn(List.of(m1,m2));

        List<MemberResponse> members = memberService.getMembers(true);
        assertEquals(2,members.size());
        assertNotNull(members.get(0).getCreated());
    }

    @Test
    void testGetMembersNotAdmin() {
        when(memberRepository.findAll()).thenReturn(List.of(m1,m2));

        List<MemberResponse> members = memberService.getMembers(false);
        assertEquals(2,members.size());
        assertNull(members.get(0).getCreated());
    }


    @Test
    void testGetMembers() {
        List<Member> members = new ArrayList<>();
        members.add(m1);
        members.add(m2);
        when(memberRepository.findAll()).thenReturn(members);

        List<MemberResponse> memberResponses = memberService.getMembers(true);
        assertEquals(2,memberResponses.size());
        assertEquals("m1",memberResponses.get(0).getUsername());
        assertEquals("m2",memberResponses.get(1).getUsername());
        assertEquals("m1@a.dk",memberResponses.get(0).getEmail());
        assertEquals("m2@a.dk",memberResponses.get(1).getEmail());
        assertEquals("bb",memberResponses.get(0).getFirstName());
        assertEquals("aa",memberResponses.get(1).getFirstName());
        assertEquals("Olsen",memberResponses.get(0).getLastName());
        assertEquals("hansen",memberResponses.get(1).getLastName());
        assertEquals("xx vej 34",memberResponses.get(0).getStreet());
        assertEquals("xx vej 34",memberResponses.get(1).getStreet());
        assertEquals("Lyngby",memberResponses.get(0).getCity());
        assertEquals("Lyngby",memberResponses.get(1).getCity());
        assertEquals("2800",memberResponses.get(0).getZip());
        assertEquals("2800",memberResponses.get(1).getZip());
    }

    @Test
    void addMember() {
        Member m3 = new Member("m3", "test12", "m3@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
        when(memberRepository.save(any(Member.class))).thenReturn(m3);
        MemberRequest request = new MemberRequest(m3);
        MemberResponse response = memberService.addMember(request);
        assertEquals("m3@a.dk", response.getEmail());
    }

    @Test
    void testEditMember(){
        MemberRequest memberEditRequest = MemberRequest.builder().password("test123").email("m1@a.dk").firstName("bb").lastName("Olsen").street("xx vej 34").city("Lyngby").zip("2800").build();
        Member editedMember = new Member(m1.getUsername(), m1.getPassword(), "m2@a.dk", m1.getFirstName(), m1.getLastName(), "Ahornsgade 25", "København", "2200");

        when(memberRepository.findById("m1")).thenReturn(Optional.of(m1));
        when(memberRepository.save(any(Member.class))).thenReturn(editedMember);

        MemberResponse editedMemberResponse = memberService.editMember(memberEditRequest, "m1");
        assertEquals("m2@a.dk", editedMemberResponse.getEmail());
        assertEquals("Ahornsgade 25", editedMemberResponse.getStreet());
        assertEquals("København", editedMemberResponse.getCity());

    }


    @Test
    void testDeleteMember() {
        Member member = new Member("m1", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
        when(memberRepository.findById("m1")).thenReturn(Optional.of(member));
        memberService.deleteMember("m1");
        Mockito.verify(memberRepository, Mockito.times(1)).delete(member);
    }

}
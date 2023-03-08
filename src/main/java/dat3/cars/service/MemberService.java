package dat3.cars.service;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Member;
import dat3.cars.repository.MemberRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MemberService {

    MemberRepository memberRepository;
    MemberRequest memberRequest;
    MemberResponse memberResponse;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> getMembers(boolean includeAll) {
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> memberResponses = members.stream().map(member -> new MemberResponse(member, includeAll)).toList();
        return memberResponses;
    }

    public MemberResponse findMemberByUsername(String username){
        Member found = memberRepository.findById(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        return new MemberResponse(found,false);
    }

    public MemberResponse addMember(MemberRequest memberRequest){
        if(memberRepository.existsById(memberRequest.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Member with this ID already exist");
        }
        if(memberRepository.existsByEmail(memberRequest.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Member with this Email already exist");
        }
        Member member = memberRepository.save(new Member(memberRequest.getUsername(), memberRequest.getPassword(), memberRequest.getEmail(), memberRequest.getFirstName(), memberRequest.getLastName(), memberRequest.getStreet(), memberRequest.getCity(), memberRequest.getZip()));
        return new MemberResponse(member, false);
    }

    public MemberResponse editMember(MemberRequest body, String username){
        Member found = memberRepository.findById(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        found.setPassword(body.getPassword());
        found.setEmail(body.getEmail());
        found.setFirstName(body.getFirstName());
        found.setLastName(body.getLastName());
        found.setStreet(body.getStreet());
        found.setCity(body.getCity());
        found.setZip(body.getZip());
        Member updated = memberRepository.save(found);
        return new MemberResponse(updated, true);
    }

    public ResponseEntity<Boolean> setRankingForUser(String username, int ranking){
        Member memberToEdit = memberRepository.findById(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,"Member with this ID does not exist"));
        memberToEdit.setRanking(ranking);
        memberRepository.save(memberToEdit);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    public void deleteMember(String username){
        Member found = memberRepository.findById(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        memberRepository.delete(found);
    }

}

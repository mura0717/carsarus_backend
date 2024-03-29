package dat3.cars.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.cars.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//Convert Member Entity to Member DTO
public class MemberResponse {
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String street;
    String city;
    String zip;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime created;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime edited;
    Integer ranking;
    Boolean approved;

    List<ReservationResponse> reservations;


    public MemberResponse(Member m, boolean includeAll) {
        this.username = m.getUsername();
        this.password = m.getPassword();
        this.email = m.getEmail();
        this.street = m.getStreet();
        this.firstName = m.getFirstName();
        this.lastName = m.getLastName();
        this.city = m.getCity();
        this.zip = m.getZip();
        if (includeAll) {
            this.created = m.getCreated();
            this.edited = m.getLastEdited();
            this.approved = m.isApproved();
            this.ranking = m.getRanking();
        }
        if(m.getReservations()!= null && m.getReservations().size()>0){
            reservations = m.getReservations().stream().map((r) -> new ReservationResponse(r)).toList();
        }


    }

}

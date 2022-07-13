package Deal.Entity;

import Conveyeor.Enums.Gender;
import Conveyeor.Enums.MaritalStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String last_name;

    private String first_name;

    private String middle_name;

    private LocalDate birth_date;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MaritalStatus marital_status;

    private Integer dependent_amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id")
    private Passport passport;

    @OneToOne
    @JoinColumn(name = "employment_id")
    private Employment employment;

    private String account;
}

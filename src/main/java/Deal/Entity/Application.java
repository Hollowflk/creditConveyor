package Deal.Entity;

import Deal.Enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDate creation_date;

    @OneToOne
    private LoanOffer appliedOffer;

    private LocalDate sign_date;

    private String ses_code;

    @OneToMany
    private List<ApplicationHistory> status_history;

    public Application(Client client) {
        this.client = client;
    }
}

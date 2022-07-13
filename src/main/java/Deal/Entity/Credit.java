package Deal.Entity;

import Deal.Enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal amount;

    private Integer term;

    private BigDecimal monthly_payment;

    private BigDecimal rate;

    private BigDecimal psk;

    @OneToMany(mappedBy = "credit", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PaymentSchedule> payment_schedule;

    private Boolean is_insurance_enabled;

    private Boolean is_salary_client;

    @Enumerated(EnumType.STRING)
    private CreditStatus credit_status;
}

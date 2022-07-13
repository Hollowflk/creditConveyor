package Deal.Entity;

import Conveyeor.Enums.PositionAtWork;
import Conveyeor.Enums.WorkStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employment")
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private WorkStatus employment_status;

    private String employer;

    private BigDecimal salary;

    private PositionAtWork position;

    private Integer work_experience_total;

    private Integer work_experience_current;
}

package Conveyeor.DTO;

import Conveyeor.Enums.PositionAtWork;
import Conveyeor.Enums.WorkStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmploymentDTO {

    @NotNull
    private WorkStatus employmentStatus;

    @NotNull
    private String employerINN;

    @NotNull
    private BigDecimal salary;

    @NotNull
    private PositionAtWork position;

    @NotNull
    private Integer workExperienceTotal;

    @NotNull
    private Integer workExperienceCurrent;
}

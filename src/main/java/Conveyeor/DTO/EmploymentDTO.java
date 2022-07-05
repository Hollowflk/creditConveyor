package Conveyeor.DTO;

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
    private Enum employmentStatus;

    @NotNull
    private String employerINN;

    @NotNull
    private BigDecimal salary;

    @NotNull
    private Enum position;

    @NotNull
    private Integer workExperienceTotal;

    @NotNull
    private Integer workExperienceCurrent;
}

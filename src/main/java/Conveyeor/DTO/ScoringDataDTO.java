package Conveyeor.DTO;

import Conveyeor.Enums.Gender;
import Conveyeor.Enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoringDataDTO {

    @Min(10000)
    @NotNull
    private BigDecimal amount;

    @Min(6)
    @NotNull
    private Integer term;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String lastName;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String middleName;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate birthdate;

    @NotEmpty
    @Size(min = 4, max = 4)
    private String passportSeries;

    @NotEmpty
    @Size(min = 6, max = 6)
    private String passportNumber;

    @NotNull
    private LocalDate passportIssueDate;

    @NotNull
    private String passportIssueBranch;

    @NotNull
    private MaritalStatus maritalStatus;

    @NotNull
    private Integer dependentAmount;

    @Valid
    private EmploymentDTO employment;

    @NotNull
    private String account;

    @NotNull
    private Boolean isInsuranceEnabled;

    @NotNull
    private Boolean isSalaryClient;
}

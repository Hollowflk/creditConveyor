package Conveyeor.DTO;

import Conveyeor.Enums.Gender;
import Conveyeor.Enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Сумма займа")
    private BigDecimal amount;

    @Min(6)
    @NotNull
    @Schema(description = "Срок займа")
    private Integer term;

    @NotEmpty
    @Size(min = 2, max = 30)
    @Schema(description = "Имя")
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 30)
    @Schema(description = "Фамилия")
    private String lastName;

    @NotEmpty
    @Size(min = 2, max = 30)
    @Schema(description = "Отчество")
    private String middleName;

    @NotNull
    @Schema(description = "Пол")
    private Gender gender;

    @NotNull
    @Schema(description = "Дата рождения гггг-мм-дд")
    private LocalDate birthdate;

    @NotEmpty
    @Size(min = 4, max = 4)
    @Schema(description = "серия паспорта")
    private String passportSeries;

    @NotEmpty
    @Size(min = 6, max = 6)
    @Schema(description = "номер паспорта")
    private String passportNumber;

    @NotNull
    @Schema(description = "Дата выдачи паспорта")
    private LocalDate passportIssueDate;

    @NotNull
    @Schema(description = "Кем выдан паспорт")
    private String passportIssueBranch;

    @NotNull
    @Schema(description = "Семейное положение")
    private MaritalStatus maritalStatus;

    @NotNull
    @Schema(description = "Кол-во иждевенцов")
    private Integer dependentAmount;

    @Valid
    private EmploymentDTO employment;

    @NotNull
    @Schema(description = "Номер счета")
    private String account;

    @NotNull
    @Schema(description = "Страховка")
    private Boolean isInsuranceEnabled;

    @NotNull
    @Schema(description = "Зарплатный клиент ?")
    private Boolean isSalaryClient;
}

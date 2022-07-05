package Conveyeor.Services;

import Conveyeor.DTO.ScoringDataDTO;
import Conveyeor.Enums.Gender;
import Conveyeor.Enums.MaritalStatus;
import Conveyeor.Enums.PositionAtWork;
import Conveyeor.Enums.WorkStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
@Slf4j
public class ScoringService {

    @Value("${baseRate}")
    private BigDecimal baseRate;

    private int ageNow(LocalDate birthdate) {
        LocalDate currentTime = LocalDate.now();
        Period period = Period.between(birthdate, currentTime);
        int age = period.getYears();

        log.debug("Method getAge return age: {}", age);

        return age;
    }

    public BigDecimal scoring(ScoringDataDTO scoringDataDTO){

        log.info("Начинаю скоринг");

        BigDecimal rate = baseRate;

        if (scoringValidate(scoringDataDTO)){
            if(scoringDataDTO.getEmployment().getEmploymentStatus().equals(WorkStatus.EMPLOYED)){
                rate = rate.add(BigDecimal.valueOf(1));
            } else if (scoringDataDTO.getEmployment().getEmploymentStatus().equals(WorkStatus.BUSINESS_OWNER)){
                rate = rate.add(BigDecimal.valueOf(3));
            }

            log.info("Рабочий статус " + scoringDataDTO.getEmployment().getEmploymentStatus());

            if (scoringDataDTO.getEmployment().getPosition().equals(PositionAtWork.MIDDLE_MANAGER)){
                rate = rate.subtract(BigDecimal.valueOf(2));
            } else if (scoringDataDTO.getEmployment().getPosition().equals(PositionAtWork.TOP_MANAGER)){
                rate = rate.subtract(BigDecimal.valueOf(4));
            }

            log.info("Позиция на работе " + scoringDataDTO.getEmployment().getPosition());

            if (scoringDataDTO.getMaritalStatus().equals(MaritalStatus.MARRIED)){
                rate = rate.subtract(BigDecimal.valueOf(3));
            } else if (scoringDataDTO.getMaritalStatus().equals(MaritalStatus.DIVORCED)){
                rate = rate.add(BigDecimal.valueOf(1));
            } else if (scoringDataDTO.getMaritalStatus().equals(MaritalStatus.SINGLE)){
                rate = rate.add(BigDecimal.valueOf(1));
            }

            log.info("Отношения " + scoringDataDTO.getMaritalStatus());

            if (scoringDataDTO.getDependentAmount() > 1){
                rate = rate.add(BigDecimal.valueOf(1));
            }

            log.info("Кол-во иждивенцев " + scoringDataDTO.getDependentAmount());

            int age = ageNow(scoringDataDTO.getBirthdate());

            if (scoringDataDTO.getGender().equals(Gender.WOMAN) && age >= 35 && age < 60 ){
                rate = rate.subtract(BigDecimal.valueOf(3));
            } else if (scoringDataDTO.getGender().equals(Gender.MAN) && age >= 30 && age < 55){
                rate = rate.subtract(BigDecimal.valueOf(3));
            }

            log.info("Пол " + scoringDataDTO.getGender() + "Возраст " + age);

            return rate;
        }

        throw new RuntimeException("Заявка не одобрена");
    }

    private Boolean scoringValidate(ScoringDataDTO scoringDataDTO){

        if (scoringDataDTO.getEmployment().getEmploymentStatus().equals(WorkStatus.UNEMPLOYED)){
            log.debug("Отказано ваш статус" + WorkStatus.UNEMPLOYED);
            return false;
        }

        if (scoringDataDTO.getEmployment().getSalary().multiply(BigDecimal.valueOf(20)).
                compareTo(scoringDataDTO.getAmount()) < 0) {
            log.debug("Отказ: сумма займа больше чем 20 зарплат");
            return false;
        }

        int age = ageNow(scoringDataDTO.getBirthdate());

        if (age < 20 || age > 60){
            log.debug("В этом возрасте мы не можем дать кредит");
            return false;
        }

        if (scoringDataDTO.getEmployment().getWorkExperienceTotal() < 12 || scoringDataDTO.getEmployment().getWorkExperienceCurrent() < 3){
            log.debug("У вас не достаточно стажа");
            return false;
        }

        return true;
    }
}

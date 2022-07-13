package Conveyeor.Services;

import Conveyeor.DTO.ScoringDataDTO;
import Conveyeor.Enums.WorkStatus;
import Conveyeor.Utils.UtilTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoringServiceTest {

    @Autowired
    private ScoringService scoringService;

    @Test
    void scoringEmploymentStatusTest(){
        ScoringDataDTO scoringDataDTO = UtilTest.createScoringDTOTest();
        scoringDataDTO.getEmployment().setEmploymentStatus(WorkStatus.UNEMPLOYED);

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,() -> {
           scoringService.scoring(scoringDataDTO);
        });

        assertEquals("Заявка не одобрена",thrown.getMessage());
    }

    @Test
    void scoringEmploymentPositionTest(){
        ScoringDataDTO scoringDataDTO = UtilTest.createScoringDTOTest();
        scoringDataDTO.setAmount(BigDecimal.valueOf(1000000));
        scoringDataDTO.getEmployment().setSalary(BigDecimal.valueOf(12000));

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,() -> {
            scoringService.scoring(scoringDataDTO);
        });

        assertEquals("Заявка не одобрена",thrown.getMessage());
    }

    @Test
    void scoringAgeTest(){
        ScoringDataDTO scoringDataDTO = UtilTest.createScoringDTOTest();
        scoringDataDTO.setBirthdate(LocalDate.now().minusYears(30));
        Period period = Period.between(scoringDataDTO.getBirthdate(),LocalDate.now());
        int age = period.getYears();

        if (age < 20){
            assertEquals(20,age);
        } else if (age > 60){
            assertEquals(60, age);
        }
    }

    @Test
    void scoringWorkExperienceTest(){
        ScoringDataDTO scoringDataDTO = UtilTest.createScoringDTOTest();
        scoringDataDTO.getEmployment().setWorkExperienceTotal(3);
        scoringDataDTO.getEmployment().setWorkExperienceCurrent(3);

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,() -> {
            scoringService.scoring(scoringDataDTO);
        });

        assertEquals("Заявка не одобрена",thrown.getMessage());
    }
}
package Conveyeor.Utils;

import Conveyeor.DTO.EmploymentDTO;
import Conveyeor.DTO.ScoringDataDTO;
import Conveyeor.Enums.Gender;
import Conveyeor.Enums.MaritalStatus;
import Conveyeor.Enums.PositionAtWork;
import Conveyeor.Enums.WorkStatus;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class UtilTest {


    public static ScoringDataDTO createScoringDTOTest(){

        EmploymentDTO employmentDTO = new EmploymentDTO(
                WorkStatus.EMPLOYED,
                "205353532",
                BigDecimal.valueOf(50000),
                PositionAtWork.MIDDLE_MANAGER,
                20,
                20
        );

        ScoringDataDTO scoringDataDTO = new ScoringDataDTO(
                BigDecimal.valueOf(300000),
                18,
                "Oliver",
                "Tree",
                "Orsen",
                Gender.MAN,
                LocalDate.now().minusYears(20),
                "1234",
                "123456",
                LocalDate.now().minusMonths(3),
                "ovd Moscow",
                MaritalStatus.SINGLE,
                1,
                employmentDTO,
                "12345",
                false,
                false
        );

        return scoringDataDTO;
    }
}

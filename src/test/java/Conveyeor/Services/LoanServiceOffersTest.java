package Conveyeor.Services;

import Conveyeor.DTO.LoanApplicationRequestDTO;
import Conveyeor.DTO.LoanOfferDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanServiceOffersTest {

    @Autowired
    LoanServiceOffers loanServiceOffers;

    @Test
    void createLoanOffersList() {

        List<LoanOfferDTO> actual = loanServiceOffers.createLoanOffersList(createLoanApplicationsRequestDTOTest());

        List<LoanOfferDTO> expected = createOffers();

        assertEquals(expected, actual);
    }

    private List<LoanOfferDTO> createOffers(){
        List<LoanOfferDTO> expected = new ArrayList<>(4);
        expected.add(
                new LoanOfferDTO(
                        1L,
                        BigDecimal.valueOf(300000),
                        BigDecimal.valueOf(336877.85142).setScale(10, RoundingMode.HALF_UP),
                        18,
                        BigDecimal.valueOf(18715.43619).setScale(10,RoundingMode.HALF_UP),
                        BigDecimal.valueOf(15),
                        false,
                        false));
        expected.add(
                new LoanOfferDTO(
                2L,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(334443.16584).setScale(10, RoundingMode.HALF_UP),
                18,
                BigDecimal.valueOf(18580.17588).setScale(10, RoundingMode.HALF_UP),
                BigDecimal.valueOf(14),
                false,
                true));
        expected.add(
                new LoanOfferDTO(
                        3L,
                        BigDecimal.valueOf(300000),
                        BigDecimal.valueOf(539070.74488).setScale(10, RoundingMode.HALF_UP),
                        18,
                        BigDecimal.valueOf(24392.81916).setScale(10, RoundingMode.HALF_UP),
                        BigDecimal.valueOf(12),
                        true,
                        false));
        expected.add(
                new LoanOfferDTO(
                        4L,
                        BigDecimal.valueOf(300000),
                        BigDecimal.valueOf(535866.71248).setScale(10, RoundingMode.HALF_UP),
                        18,
                        BigDecimal.valueOf(24214.81736).setScale(10, RoundingMode.HALF_UP),
                        BigDecimal.valueOf(11),
                        true,
                        true));

        return expected;
    }

    public LoanApplicationRequestDTO createLoanApplicationsRequestDTOTest() {
        LoanApplicationRequestDTO loanApplicationRequestDTO = new LoanApplicationRequestDTO();
        loanApplicationRequestDTO.setAmount(BigDecimal.valueOf(300000));
        loanApplicationRequestDTO.setTerm(18);
        loanApplicationRequestDTO.setFirstName("Oliver");
        loanApplicationRequestDTO.setLastName("Makgri");
        loanApplicationRequestDTO.setMiddleName("Wilson");
        loanApplicationRequestDTO.setEmail("Makgri@gmail.com");
        loanApplicationRequestDTO.setBirthdate(LocalDate.now().minusYears(20));
        loanApplicationRequestDTO.setPassportNumber("1456");
        loanApplicationRequestDTO.setPassportSeries("232567");
        return loanApplicationRequestDTO;
    }
}
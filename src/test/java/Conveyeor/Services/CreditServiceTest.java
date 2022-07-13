package Conveyeor.Services;

import Conveyeor.DTO.CreditDTO;

import Conveyeor.Utils.UtilTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CreditServiceTest {

    @Autowired
    private CreditService creditService;

    @Test
    public void createCreditTest(){

        CreditDTO creditDTO = creditService.createCredit(UtilTest.createScoringDTOTest());

        assertEquals(BigDecimal.valueOf(15), creditDTO.getRate());
        assertEquals(BigDecimal.valueOf(18715.43619),creditDTO.getMonthlyPayment().setScale(5, RoundingMode.HALF_UP));
        assertEquals(BigDecimal.valueOf(15.01), creditDTO.getPsk());

    }

}
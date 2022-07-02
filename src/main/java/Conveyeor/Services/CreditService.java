package Conveyeor.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class CreditService {

    public BigDecimal monthlyPayment(BigDecimal amount, Integer term, BigDecimal rate){

        log.info("Расчет ежемесячного платежа");

        BigDecimal monthlyInterestRate = rate.divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);
        BigDecimal poweredRate = BigDecimal.ONE.add(monthlyInterestRate).pow(term);
        BigDecimal annuityConf = monthlyInterestRate.multiply(poweredRate).divide(poweredRate.subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = annuityConf.multiply(amount);

        return monthlyPayment;
    }
}

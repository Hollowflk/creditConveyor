package Conveyeor.Services;

import Conveyeor.DTO.CreditDTO;
import Conveyeor.DTO.PaymentScheduleElement;
import Conveyeor.DTO.ScoringDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.Duration.between;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditService {

    @Value("${Insurance}")
    private BigDecimal INSURANCE;
    private final ScoringService scoringService;

    public CreditDTO createCredit(ScoringDataDTO scoringDataDTO){

        log.info("Начинаю создание кредита");

        BigDecimal rate = scoringService.scoring(scoringDataDTO);

        BigDecimal amount = scoringDataDTO.getAmount();

        if (scoringDataDTO.getIsInsuranceEnabled()){
            amount = amount.add(INSURANCE);
        }

        BigDecimal monthPayment = monthlyPayment(amount, scoringDataDTO.getTerm(),rate);

        List<PaymentScheduleElement> paymentScheduleElementsList = paymentScheduleList(amount,scoringDataDTO.getTerm(),rate,monthPayment);

        BigDecimal psk = psk(paymentScheduleElementsList,amount);

        CreditDTO creditDTO = CreditDTO.builder()
                .amount(amount)
                .term(scoringDataDTO.getTerm())
                .monthlyPayment(monthPayment)
                .rate(rate)
                .psk(psk)
                .isInsuranceEnabled(scoringDataDTO.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDTO.getIsSalaryClient())
                .paymentSchedule(paymentScheduleElementsList)
                .build();

        return creditDTO;
    }

    public BigDecimal monthlyPayment(BigDecimal amount, Integer term, BigDecimal rate){

        log.info("Расчет ежемесячного платежа");

        BigDecimal percentPerMonth = rate.divide(BigDecimal.valueOf(1200),4,RoundingMode.HALF_UP);
        BigDecimal annuityRatio = percentPerMonth.multiply(BigDecimal.ONE.add(percentPerMonth).pow(term))
                .divide((BigDecimal.ONE.add(percentPerMonth)).pow(term).subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP);
        BigDecimal monthPayment = amount.multiply(annuityRatio);

        log.info("Расчет окончен " + monthPayment);

        return monthPayment;
    }

    private BigDecimal interestPayment(BigDecimal remainInDebt, BigDecimal rate,LocalDate days){

        long countOfDays = between(days.minusMonths(1).atStartOfDay(), days.atStartOfDay()).toDays();

        log.info("Расчет процентов");
        BigDecimal interestPayment = remainInDebt.multiply(rate.divide(BigDecimal.valueOf(100),10,RoundingMode.HALF_UP)).
                multiply(BigDecimal.valueOf(countOfDays)).divide((BigDecimal.valueOf(365)),2,RoundingMode.HALF_UP);

        return interestPayment;
    }

    private BigDecimal psk(List<PaymentScheduleElement> paymentScheduleElementList,BigDecimal amount){

        log.info("Расчет ПСК");

        int size = paymentScheduleElementList.size() + 1;

        LocalDate[] dates = new LocalDate[size];
        dates[0] = paymentScheduleElementList.get(0).getDate().minusMonths(1);
        for (int i = 1; i < size;i++ ){
            dates[i] = paymentScheduleElementList.get(i - 1).getDate();
        }

        long[] days = new long[size];
        for (int i = 0; i < size; i++){
            days[i] = between(dates[0].atStartOfDay(),dates[i].atStartOfDay()).toDays();
        }

        double[] sum = new double[size];
        sum[0] = - amount.doubleValue();
        for (int i = 1; i < size; i++){
            sum[i] = paymentScheduleElementList.get(i - 1).getTotalPayment().doubleValue();
        }

        double basePeriod = 30d;
        double basePeriodYear = 365 / basePeriod;

        double[] e = new double[size];
        double[] q = new double[size];
        for (int k = 0; k < size; k++){
            e[k] = (days[k] % basePeriod) / basePeriod;
            q[k] = Math.floor(days[k] / basePeriod);
        }

        log.info("Расчет i");

        double i = 0;
        double x = 1;
        double s = 0.00001;
        while (x > 0){
            x = 0;
            for (int k = 0; k < size; k++){
                x = x + sum[k] / ((1 + e[k] * i) * Math.pow(1 + i, q[k]));
            }
            i = i + s;
        }

        log.info("Расчет i окончен " + i);

        double psk = Math.floor(i * basePeriodYear * 100 * 1000) / 1000;

        return BigDecimal.valueOf(psk).setScale(2, RoundingMode.HALF_UP);
    }

    private List<PaymentScheduleElement> paymentScheduleList (BigDecimal amount, Integer term, BigDecimal rate, BigDecimal monthPayment){

        log.info("Создание списка платежей");

        List<PaymentScheduleElement> paymentScheduleElementList = new ArrayList<>();

        BigDecimal remainInDebt = amount;


        for (int i = 1; i <= term + 1; i++){

            LocalDate paymentDate = LocalDate.now().plusMonths(i);

            BigDecimal interestPayment = interestPayment(remainInDebt, rate ,paymentDate);

            BigDecimal debtPayment = monthPayment.subtract(interestPayment).setScale(2,RoundingMode.HALF_UP);

            remainInDebt = remainInDebt.subtract(debtPayment);

            PaymentScheduleElement paymentScheduleElement = PaymentScheduleElement.builder()
                    .number(i)
                    .date(paymentDate)
                    .totalPayment(monthPayment)
                    .interestPayment(interestPayment)
                    .debtPayment(debtPayment)
                    .remainInDebt(remainInDebt)
                    .build();

            paymentScheduleElementList.add(paymentScheduleElement);
        }

        PaymentScheduleElement lastElement = paymentScheduleElementList.get(paymentScheduleElementList.size() - 1);

        lastElement.setDebtPayment(lastElement.getDebtPayment().add(remainInDebt));
        lastElement.setTotalPayment(lastElement.getTotalPayment().add(remainInDebt));
        lastElement.setRemainInDebt(lastElement.getRemainInDebt().subtract(remainInDebt));

        log.info("Создание списка платежей оконченно");

        return paymentScheduleElementList;

    }
}

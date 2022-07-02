package Conveyeor.Services;

import Conveyeor.DTO.LoanApplicationRequestDTO;
import Conveyeor.DTO.LoanOfferDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoanServiceOffers {

    private static final BigDecimal INSURANCE = BigDecimal.valueOf(100000);
    private static Long APPLICATION_ID = 0l;
    private final CreditService creditService;

    @Value("${baseRate}")
    private BigDecimal baseRate;


    public List<LoanOfferDTO> createLoanOffersList(LoanApplicationRequestDTO loanApplicationRequestDTO){

        log.info("Начинаю создание Кредитных предложений");

        List<LoanOfferDTO> loanOfferDTOList = new ArrayList<>();

        loanOfferDTOList.add(createLoanOffer(loanApplicationRequestDTO,false,false));
        loanOfferDTOList.add(createLoanOffer(loanApplicationRequestDTO,false,true));
        loanOfferDTOList.add(createLoanOffer(loanApplicationRequestDTO,true,false));
        loanOfferDTOList.add(createLoanOffer(loanApplicationRequestDTO,true,true));

        Collections.sort(loanOfferDTOList, new Comparator<LoanOfferDTO>() {
            @Override
            public int compare(LoanOfferDTO o1, LoanOfferDTO o2) {
                return o2.getRate().compareTo(o1.getRate());
            }
        });

        return loanOfferDTOList;
    }

    private LoanOfferDTO createLoanOffer (LoanApplicationRequestDTO loanApplicationRequestDTO, boolean isInsuranceEnabled, boolean isSalaryClient){

        log.info("Создание предложения");

        BigDecimal rate = baseRate;

        if(isInsuranceEnabled){
            rate = rate.subtract(new BigDecimal(3));
        }

        if (isSalaryClient){
            rate = rate.subtract(new BigDecimal(1));
        }

        BigDecimal monthlyPayment = creditService.monthlyPayment(loanApplicationRequestDTO.getAmount(),loanApplicationRequestDTO.getTerm(), rate);
        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(loanApplicationRequestDTO.getTerm()));

        LoanOfferDTO loanOfferDTO = LoanOfferDTO.builder()
                .applicationId(++APPLICATION_ID)
                .requestedAmount(loanApplicationRequestDTO.getAmount())
                .totalAmount(totalAmount)
                .term(loanApplicationRequestDTO.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();

        log.info("Предложение создано");

        return loanOfferDTO;
    }
}

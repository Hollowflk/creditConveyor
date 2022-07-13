package Deal.Services;

import Conveyeor.DTO.LoanApplicationRequestDTO;
import Conveyeor.DTO.LoanOfferDTO;
import Deal.Entity.Application;
import Deal.Entity.Client;
import Deal.Entity.Passport;
import Deal.Util.FeignClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealService {

    private final ClientService clientService;

    private final ApplicationService applicationService;

    private final FeignClientService feignClientService;

    public List<LoanOfferDTO> createApplication(LoanApplicationRequestDTO requestDTO) {

        log.info("Create application");

        Client client = createClient(requestDTO);

        Application application = new Application(client);


        log.info("Applications created");

        applicationService.save(application);

        log.info("Application saved");

        List<LoanOfferDTO> loanOfferDTOList = feignClientService.getLoanOffer(requestDTO);

        for (LoanOfferDTO loanOfferDTO: loanOfferDTOList){
            loanOfferDTO.setApplicationId(application.getId());
        }

        log.info("Created loanOffersList");

        return loanOfferDTOList;

    }

    private Client createClient (LoanApplicationRequestDTO requestDTO) {

        log.info("Create client");

        Client client = Client.builder()
                .last_name(requestDTO.getLastName())
                .first_name(requestDTO.getFirstName())
                .middle_name(requestDTO.getMiddleName())
                .birth_date(requestDTO.getBirthdate())
                .email(requestDTO.getEmail())
                .build();

        log.info("Client created");

        clientService.save(client);

        log.info("Client saved");

        return client;
    }

    private Passport createPassport(LoanApplicationRequestDTO loanApplicationRequestDTO){

        log.info("Create passport");

       Passport passport = Passport.builder()
               .series(loanApplicationRequestDTO.getPassportSeries())
               .number(loanApplicationRequestDTO.getPassportNumber())
               .build();

       log.info("passport is created");

       return passport;
    }
}

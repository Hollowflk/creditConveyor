package Deal.Util;

import Conveyeor.DTO.LoanApplicationRequestDTO;
import Conveyeor.DTO.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "creditConveyor", url = "http://localhost:8080/conveyor")
public interface FeignClientService {

    @RequestMapping(method = RequestMethod.POST, value = "/offers")
    List<LoanOfferDTO> getLoanOffer (@Valid @RequestBody LoanApplicationRequestDTO requestDTO);
}

package Deal.Controllers;

import Conveyeor.DTO.LoanApplicationRequestDTO;
import Conveyeor.DTO.LoanOfferDTO;
import Deal.Services.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deal")
public class DealController {

    private final DealService dealService;

    @PostMapping("/application")
    public List<LoanOfferDTO> crateLoanOffersList (@RequestBody LoanApplicationRequestDTO requestDTO) {
        return dealService.createApplication(requestDTO);
    }
}

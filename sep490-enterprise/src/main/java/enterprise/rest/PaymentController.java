package enterprise.rest;

import commons.springfw.impl.mappers.CommonMapper;
import enterprise.dtos.PaymentCriteriaDTO;
import enterprise.dtos.PaymentDTO;
import enterprise.mappers.PaymentMapper;
import enterprise.services.PaymentService;
import green_buildings.commons.api.dto.SearchCriteriaDTO;
import green_buildings.commons.api.dto.SearchResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;
    
    @PostMapping("/search")
    public ResponseEntity<SearchResultDTO<PaymentDTO>> searchPayment(@RequestBody SearchCriteriaDTO<PaymentCriteriaDTO> searchCriteria) {
        var pageable =  CommonMapper.toPageable(searchCriteria.page(), searchCriteria.sort());
        var searchResults = paymentService.search(searchCriteria, pageable);
        return ResponseEntity.ok(
                CommonMapper.toSearchResultDTO(
                        searchResults,
                        paymentMapper::paymentEntityToPaymentDTO));
    }
}

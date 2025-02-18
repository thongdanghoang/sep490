package enterprise.services;

import enterprise.dtos.PaymentCriteriaDTO;
import enterprise.entities.PaymentEntity;
import green_buildings.commons.api.dto.SearchCriteriaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface PaymentService {
    
    Page<PaymentEntity> search(SearchCriteriaDTO<PaymentCriteriaDTO> searchCriteria, Pageable pageable);
    
    PaymentEntity createPayment(UUID id);
    
    void updatePaymentInfo(Long orderCode);
}

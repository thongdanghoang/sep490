package enterprise.services;

import enterprise.dtos.PaymentCriteriaDTO;
import enterprise.entities.PaymentEntity;
import green_buildings.commons.api.dto.SearchCriteriaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PaymentService {
    
    Page<PaymentEntity> search(SearchCriteriaDTO<PaymentCriteriaDTO> searchCriteria, Pageable pageable);
}

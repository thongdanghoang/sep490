package enterprise.services.impl;

import commons.springfw.impl.utils.SecurityUtils;
import enterprise.dtos.PaymentCriteriaDTO;
import enterprise.entities.PaymentEntity;
import enterprise.repositories.PaymentRepository;
import enterprise.services.PaymentService;
import green_buildings.commons.api.dto.SearchCriteriaDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(rollbackOn = Throwable.class)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository payRepo;
    
    @Override
    public Page<PaymentEntity> search(SearchCriteriaDTO<PaymentCriteriaDTO> searchCriteria, Pageable pageable) {
     //   UUID enterpriseId = SecurityUtils.getCurrentUserEnterpriseId().orElseThrow();
        return payRepo.findByName(
                searchCriteria.criteria().criteria(),
     //           enterpriseId,
                  pageable);
    }
}

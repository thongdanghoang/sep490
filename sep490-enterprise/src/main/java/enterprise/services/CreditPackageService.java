package enterprise.services;

import enterprise.dtos.PaymentCriteriaDTO;
import enterprise.entities.CreditPackageEntity;
import green_buildings.commons.api.dto.SearchCriteriaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CreditPackageService {
    
    List<CreditPackageEntity> findAll();
    
    Optional<CreditPackageEntity> findById(UUID id);

    void createOrUpdate(CreditPackageEntity creditPackage);

    void deletePackages(Set<UUID> packageIds);

    Page<CreditPackageEntity> search(Pageable pageable);
}

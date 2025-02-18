package enterprise.services;

import enterprise.entities.CreditPackageEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CreditPackageService {
    
    List<CreditPackageEntity> findAll();
    
    Optional<CreditPackageEntity> findById(UUID id);

    void createOrUpdate(CreditPackageEntity creditPackage);

    void deletePackages(Set<UUID> packageIds);
}

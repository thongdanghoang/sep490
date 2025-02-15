package enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import enterprise.entities.WalletEntity;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends AbstractBaseRepository<WalletEntity> {
    Optional<WalletEntity> findByEnterpriseId(UUID enterpriseId);
}

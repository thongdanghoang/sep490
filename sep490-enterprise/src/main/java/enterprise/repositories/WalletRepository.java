package enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import enterprise.entities.WalletEntity;

import java.util.UUID;

public interface WalletRepository extends AbstractBaseRepository<WalletEntity> {
    WalletEntity findByEnterpriseId(UUID enterpriseId);
}

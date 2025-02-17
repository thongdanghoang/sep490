package enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import enterprise.entities.CreditPackageEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CreditPackageRepository extends AbstractBaseRepository<CreditPackageEntity> {
    @Query("""
            SELECT c
            FROM CreditPackageEntity c
            WHERE c.id IN :ids
            """
    )
    List<CreditPackageEntity> findByIDs(Set<UUID> ids);
}

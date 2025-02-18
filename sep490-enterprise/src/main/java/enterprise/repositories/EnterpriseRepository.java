package enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import enterprise.entities.EnterpriseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepository extends AbstractBaseRepository<EnterpriseEntity> {
}

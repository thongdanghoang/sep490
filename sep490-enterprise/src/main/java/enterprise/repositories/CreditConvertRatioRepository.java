package enterprise.repositories;

import enterprise.entities.CreditConvertRatioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditConvertRatioRepository extends JpaRepository<CreditConvertRatioEntity, UUID> {
}

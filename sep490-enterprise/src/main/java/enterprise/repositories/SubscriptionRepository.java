package enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import enterprise.entities.SubscriptionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends AbstractBaseRepository<SubscriptionEntity> {
    
    @Query(value = """
                select s from SubscriptionEntity s
                    where s.startDate <= :now
                        and s.endDate >= :now
            """)
    List<SubscriptionEntity> findAllValidSubscriptions(@Param("now") LocalDate now);
    
}

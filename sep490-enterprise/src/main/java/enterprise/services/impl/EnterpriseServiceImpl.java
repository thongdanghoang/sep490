package enterprise.services.impl;

import enterprise.entities.EnterpriseEntity;
import enterprise.repositories.EnterpriseRepository;
import enterprise.services.EnterpriseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional(rollbackOn = Throwable.class)
@RequiredArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {
    
    private final EnterpriseRepository repository;
    
    @Override
    public UUID createEnterprise(EnterpriseEntity enterprise) {
        return repository.save(enterprise).getId();
    }
}

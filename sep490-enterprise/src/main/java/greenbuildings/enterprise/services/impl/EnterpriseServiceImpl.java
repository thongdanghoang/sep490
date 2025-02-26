package greenbuildings.enterprise.services.impl;

import greenbuildings.enterprise.entities.EnterpriseEntity;
import greenbuildings.enterprise.repositories.EnterpriseRepository;
import greenbuildings.enterprise.services.EnterpriseService;
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
    
    @Override
    public EnterpriseEntity getById(UUID id) {
        return repository.findById(id).orElseThrow();
    }
}

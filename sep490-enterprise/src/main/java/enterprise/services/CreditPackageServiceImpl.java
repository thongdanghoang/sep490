package enterprise.services;

import enterprise.entities.CreditPackageEntity;
import enterprise.repositories.CreditPackageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Throwable.class)
public class CreditPackageServiceImpl implements CreditPackageService {
    
    private final CreditPackageRepository creditPackageRepository;
    
    @Override
    public List<CreditPackageEntity> findAll() {
        return creditPackageRepository.findAll();
    }
    
    @Override
    public Optional<CreditPackageEntity> findById(UUID id) {
        return creditPackageRepository.findById(id);
    }
}

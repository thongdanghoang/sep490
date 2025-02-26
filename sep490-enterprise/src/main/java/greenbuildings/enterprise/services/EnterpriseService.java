package greenbuildings.enterprise.services;

import greenbuildings.enterprise.entities.EnterpriseEntity;

import java.util.UUID;

public interface EnterpriseService {
    
    UUID createEnterprise(EnterpriseEntity enterprise);
    
    EnterpriseEntity getById(UUID id);
    
}

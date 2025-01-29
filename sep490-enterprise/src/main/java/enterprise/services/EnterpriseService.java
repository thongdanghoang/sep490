package enterprise.services;

import enterprise.entities.EnterpriseEntity;

import java.util.UUID;

public interface EnterpriseService {
    
    UUID createEnterprise(EnterpriseEntity enterprise);
    
}

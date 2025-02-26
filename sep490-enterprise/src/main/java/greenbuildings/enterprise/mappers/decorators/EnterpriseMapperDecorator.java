package greenbuildings.enterprise.mappers.decorators;

import greenbuildings.enterprise.dtos.EnterpriseDTO;
import greenbuildings.enterprise.entities.EnterpriseEntity;
import greenbuildings.enterprise.entities.WalletEntity;
import greenbuildings.enterprise.mappers.EnterpriseMapper;
import greenbuildings.commons.api.events.PendingEnterpriseRegisterEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class EnterpriseMapperDecorator implements EnterpriseMapper {
    
    @Autowired
    @Qualifier("delegate")
    private EnterpriseMapper delegate;
    
    @Override
    public EnterpriseEntity createEnterprise(EnterpriseDTO enterpriseDTO) {
        EnterpriseEntity enterprise = delegate.createEnterprise(enterpriseDTO);
        enterprise.setWallet(WalletEntity.of(enterprise));
        return enterprise;
    }
    
    @Override
    public EnterpriseEntity createEnterprise(PendingEnterpriseRegisterEvent event) {
        EnterpriseEntity enterprise = delegate.createEnterprise(event);
        enterprise.setWallet(WalletEntity.of(enterprise));
        return enterprise;
    }
}

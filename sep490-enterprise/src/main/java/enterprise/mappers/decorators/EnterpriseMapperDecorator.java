package enterprise.mappers.decorators;

import enterprise.dtos.EnterpriseDTO;
import enterprise.entities.EnterpriseEntity;
import enterprise.entities.WalletEntity;
import enterprise.mappers.EnterpriseMapper;
import green_buildings.commons.api.events.PendingEnterpriseRegisterEvent;
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

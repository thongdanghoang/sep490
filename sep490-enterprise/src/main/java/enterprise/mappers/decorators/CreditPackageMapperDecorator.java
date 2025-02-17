package enterprise.mappers.decorators;

import enterprise.dtos.CreditPackageDTO;
import enterprise.entities.CreditPackageEntity;
import enterprise.mappers.CreditPackageMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class CreditPackageMapperDecorator implements CreditPackageMapper {

    @Autowired
    @Qualifier("delegate")
    private CreditPackageMapper delegate;


    @Override
    public CreditPackageEntity dtoToCreateCreditPackage(CreditPackageDTO dto) {
        return delegate.dtoToCreateCreditPackage(dto);
    }

    @Override
    public CreditPackageEntity dtoToUpdateCreditPackage(CreditPackageEntity entity, CreditPackageDTO dto) {
       return delegate.dtoToUpdateCreditPackage(entity, dto);
    }
}

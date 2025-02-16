package enterprise.services;

import enterprise.entities.CreditPackageEntity;

import java.util.List;

public interface CreditPackageService {
    List<CreditPackageEntity> findAll();
}

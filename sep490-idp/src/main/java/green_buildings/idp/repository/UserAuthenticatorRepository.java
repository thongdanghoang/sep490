package green_buildings.idp.repository;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import green_buildings.idp.entity.UserAuthenticator;
import green_buildings.idp.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserAuthenticatorRepository extends AbstractBaseRepository<UserAuthenticator> {
    
    Optional<UserAuthenticator> findByCredentialId(String credentialId);
    
    List<UserAuthenticator> findUserAuthenticatorByUser(UserEntity user);
    
    long deleteByCredentialIdAndUser(String id, UserEntity user);
    
}
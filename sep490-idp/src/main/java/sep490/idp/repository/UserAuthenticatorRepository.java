package sep490.idp.repository;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import sep490.idp.entity.UserAuthenticator;
import sep490.idp.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserAuthenticatorRepository extends AbstractBaseRepository<UserAuthenticator> {
    
    Optional<UserAuthenticator> findByCredentialId(String credentialId);
    
    List<UserAuthenticator> findUserAuthenticatorByUser(UserEntity user);
    
    long deleteByCredentialIdAndUser(String id, UserEntity user);
    
}
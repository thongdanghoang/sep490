package sep490.idp.repository;

import org.springframework.data.repository.CrudRepository;
import sep490.idp.entity.UserAuthenticator;
import sep490.idp.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAuthenticatorRepository extends CrudRepository<UserAuthenticator, UUID> {

    Optional<UserAuthenticator> findByCredentialId(String credentialId);

    List<UserAuthenticator> findUserAuthenticatorByUser(UserEntity user);

    long deleteByCredentialIdAndUser(String id, UserEntity user);

}
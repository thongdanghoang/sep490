package sep490.idp.repository;

import org.springframework.data.repository.CrudRepository;
import sep490.idp.entity.UserAuthenticator;
import sep490.idp.entity.UserEntity;

import java.util.List;

public interface UserAuthenticatorRepository extends CrudRepository<UserAuthenticator, String> {

    List<UserAuthenticator> findUserAuthenticatorByUser(UserEntity user);

    long deleteByIdAndUser(String id, UserEntity user);

}
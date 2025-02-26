package greenbuildings.idp.repository;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import org.springframework.stereotype.Repository;
import greenbuildings.idp.entity.UserOTP;

import java.util.Optional;

@Repository
public interface UserOTPRepository extends AbstractBaseRepository<UserOTP> {

    Optional<UserOTP> findByUserEmail(String userEmail);

}

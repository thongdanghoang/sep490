package sep490.idp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sep490.idp.entity.UserOTP;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserOTPRepository extends JpaRepository<UserOTP, UUID> {

    Optional<UserOTP> findByUserEmail(String userEmail);

}

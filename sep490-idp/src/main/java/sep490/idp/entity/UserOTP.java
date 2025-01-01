package sep490.idp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sep490.common.api.utils.SEPUtils;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_otp")
@Getter
@NoArgsConstructor
public class UserOTP extends AbstractBaseEntity {

    @Column(name = "otp_code", nullable = false)
    private String otpCode;

    @Column(name = "expired_time", nullable = false)
    private LocalDateTime expiredTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;



    public void updateOtp(UserEntity user) {
        this.user = user;
        this.otpCode = SEPUtils.generateRandomOTP(6);
        this.expiredTime = LocalDateTime.now().plusMinutes(10);
    }

}

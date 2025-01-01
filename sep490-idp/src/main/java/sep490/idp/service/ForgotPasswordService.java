package sep490.idp.service;

import sep490.idp.dto.ForgotResetPasswordDTO;

public interface ForgotPasswordService {
    boolean sendResetPasswordEmail(String email);

    boolean verifyOtp(String otpCode, String email);

    boolean changePassword(ForgotResetPasswordDTO resetPasswordDTO, String forgotPasswordEmail);
}

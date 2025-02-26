package greenbuildings.idp.service;

import greenbuildings.idp.dto.ForgotResetPasswordDTO;

public interface ForgotPasswordService {
    boolean sendResetPasswordEmail(String email);

    boolean verifyOtp(String otpCode, String email);

    boolean changePassword(ForgotResetPasswordDTO resetPasswordDTO, String forgotPasswordEmail);
}

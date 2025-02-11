package green_buildings.idp.service;

import green_buildings.idp.dto.ForgotResetPasswordDTO;

public interface ForgotPasswordService {
    boolean sendResetPasswordEmail(String email);

    boolean verifyOtp(String otpCode, String email);

    boolean changePassword(ForgotResetPasswordDTO resetPasswordDTO, String forgotPasswordEmail);
}

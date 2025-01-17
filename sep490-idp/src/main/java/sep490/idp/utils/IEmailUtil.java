package sep490.idp.utils;

public interface IEmailUtil {

    void sendMail(SEPMailMessage mailMessage);

    String maskEmail(String email);
}

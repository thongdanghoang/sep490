package green_buildings.idp.utils;

public interface IEmailUtil {

    void sendMail(SEPMailMessage mailMessage);

    String maskEmail(String email);
}

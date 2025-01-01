package sep490.idp.utils;

public interface IMessageUtil {
    String getMessage(String code, Object... args);
    String getMessage(String code);
}

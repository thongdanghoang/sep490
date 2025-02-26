package greenbuildings.idp.utils;

public interface IMessageUtil {
    String getMessage(String code, Object... args);
    String getMessage(String code);
}

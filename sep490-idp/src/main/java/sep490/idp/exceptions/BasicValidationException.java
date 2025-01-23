package sep490.idp.exceptions;

import org.springframework.http.HttpStatus;
import sep490.common.api.exceptions.BusinessErrorParam;
import sep490.common.api.exceptions.BusinessException;

import java.util.List;

public class BasicValidationException extends BusinessException {
    public BasicValidationException(String field, String i18nKey, List<BusinessErrorParam> args) {
        super(field,
              i18nKey,
              args,
              HttpStatus.BAD_REQUEST.value());
    }
}

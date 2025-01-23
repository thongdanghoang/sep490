package sep490.idp.exceptions;

import org.springframework.http.HttpStatus;
import sep490.common.api.exceptions.BusinessErrorParam;
import sep490.common.api.exceptions.BusinessException;

import java.util.List;

public class BasicValidationException extends BusinessException {
    /**
     * Constructs a new BasicValidationException for a specific validation error.
     *
     * @param field The name of the field that failed validation
     * @param i18nKey The internationalization key for retrieving a localized error message
     * @param args A list of additional error parameters for message formatting
     */
    public BasicValidationException(String field, String i18nKey, List<BusinessErrorParam> args) {
        super(field,
              i18nKey,
              args,
              HttpStatus.BAD_REQUEST.value());
    }
}

package commons.springfw.impl.exceptions;

import org.springframework.http.HttpStatus;
import green_buildings.commons.api.exceptions.BusinessErrorParam;
import green_buildings.commons.api.exceptions.BusinessException;

import java.util.List;

public class JakartaValidationException extends BusinessException {
    public JakartaValidationException(String field, String i18nKey, List<BusinessErrorParam> args) {
        super(field, i18nKey, args, HttpStatus.BAD_REQUEST.value());
    }
}

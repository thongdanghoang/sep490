package sep490.idp.exceptions;

import jakarta.validation.ConstraintViolation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import sep490.common.api.exceptions.BusinessException;

import java.util.Collections;
import java.util.Set;

public class BasicValidationException extends BusinessException {
    public BasicValidationException(Set<ConstraintViolation<Object>> violations) {
        super(violations.stream()
                        .map(ConstraintViolation::getPropertyPath)
                        .map(Object::toString)
                        .reduce("%s %s"::formatted)
                        .orElse(StringUtils.EMPTY),
              null,
              Collections.emptyList(),
              HttpStatus.BAD_REQUEST.value());
    }
}

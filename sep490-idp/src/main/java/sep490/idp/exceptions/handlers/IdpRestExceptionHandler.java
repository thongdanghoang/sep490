package sep490.idp.exceptions.handlers;

import commons.springfw.impl.exceptions.handlers.RestExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "sep490.idp.rest")
public class IdpRestExceptionHandler extends RestExceptionHandler {
}

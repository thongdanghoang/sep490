package greenbuildings.idp.exceptions.handlers;

import commons.springfw.impl.exceptions.handlers.RestExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "greenbuildings.idp.rest")
public class IdpRestExceptionHandler extends RestExceptionHandler {
}

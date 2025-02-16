package green_buildings.idp.exceptions.handlers;

import commons.springfw.impl.exceptions.handlers.RestExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "green_buildings.idp.rest")
public class IdpRestExceptionHandler extends RestExceptionHandler {
}

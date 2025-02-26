package greenbuildings.idp.filters;

import commons.springfw.impl.filters.RequestBodyUnawareValidation;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "sep490.idp.rest")
public class IdpRequestBodyUnawareValidation extends RequestBodyUnawareValidation {

}

package spotease.common.api.exceptions.client;

import spotease.common.api.exceptions.BusinessErrorParam;
import spotease.common.api.exceptions.BusinessException;

import java.util.List;

/**
 * Generic business exception class to transform error from rest client
 */
public class RestClientBusinessException extends BusinessException {

    /**
     * Extends from BusinessException
     *
     * @param field   The name of the field associated with the error.
     * @param i18nKey The key used for internationalization.
     * @param args    The list of parameters for the error.
     */
    public RestClientBusinessException(String field, String i18nKey, List<BusinessErrorParam> args) {
        super(field, i18nKey, args);
    }
}

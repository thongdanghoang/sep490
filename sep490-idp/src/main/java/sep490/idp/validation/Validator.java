package sep490.idp.validation;

import java.util.Optional;

public interface Validator<T extends ToValidated> {


    /**
     * Retrieves the first validation message for the given toValidate object.
     *
     * @param toValidate the object to be validated
     * @return the first validation message as a string
     */
    Optional<String> getValidateFirstMessage(T toValidate);
}

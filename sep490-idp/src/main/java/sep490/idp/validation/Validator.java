package sep490.idp.validation;

public interface Validator<T extends ToValidated> {


    /**
     * Retrieves the first validation message for the given toValidate object.
     *
     * @param toValidate the object to be validated
     * @return the first validation message as a string
     */
    String getValidateFirstMessage(T toValidate);
}

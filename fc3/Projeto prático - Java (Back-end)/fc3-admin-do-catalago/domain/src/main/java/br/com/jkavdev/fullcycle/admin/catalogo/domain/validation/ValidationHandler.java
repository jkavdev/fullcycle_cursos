package br.com.jkavdev.fullcycle.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler handler);

    ValidationHandler validate(Validation aValidation);

    List<Error> getErrors();

    default Error firstError() {
        return hasError() ? getErrors().get(0) : null;
    }

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }


    interface Validation {
        void validate();
    }
}

package br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.handler;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.Error;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(List.of(anError));
    }

    @Override
    public ValidationHandler append(final ValidationHandler handler) {
        throw DomainException.with(handler.getErrors());
    }

    @Override
    public ValidationHandler validate(final Validation aValidation) {

        try {
            aValidation.validate();
        } catch (final Exception e) {
            throw DomainException.with(List.of(new Error(e.getMessage())));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return null;
    }
}

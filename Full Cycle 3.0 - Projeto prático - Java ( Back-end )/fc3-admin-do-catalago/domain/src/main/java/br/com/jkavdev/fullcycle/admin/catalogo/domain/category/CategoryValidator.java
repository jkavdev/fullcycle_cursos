package br.com.jkavdev.fullcycle.admin.catalogo.domain.category;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.Error;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.ValidationHandler;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        category = aCategory;
    }

    @Override
    public void validate() {
        if (this.category.getName() == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
        }
    }
}

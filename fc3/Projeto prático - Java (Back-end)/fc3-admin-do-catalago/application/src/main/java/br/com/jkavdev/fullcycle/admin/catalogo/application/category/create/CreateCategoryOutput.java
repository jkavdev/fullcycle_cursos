package br.com.jkavdev.fullcycle.admin.catalogo.application.category.create;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.Category;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;

public record CreateCategoryOutput(
        String id
) {

    public static CreateCategoryOutput from(
            final Category aCategory
    ) {
        return new CreateCategoryOutput(aCategory.getId().getValue());
    }

    public static CreateCategoryOutput from(CategoryID anId) {
        return new CreateCategoryOutput(anId.getValue());
    }
}

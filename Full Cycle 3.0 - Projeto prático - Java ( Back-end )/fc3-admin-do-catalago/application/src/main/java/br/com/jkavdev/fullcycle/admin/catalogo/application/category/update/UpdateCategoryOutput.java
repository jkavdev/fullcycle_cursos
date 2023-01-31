package br.com.jkavdev.fullcycle.admin.catalogo.application.category.update;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.Category;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {

    public static UpdateCategoryOutput from(
            final Category aCategory
    ) {
        return new UpdateCategoryOutput(aCategory.getId());
    }
}

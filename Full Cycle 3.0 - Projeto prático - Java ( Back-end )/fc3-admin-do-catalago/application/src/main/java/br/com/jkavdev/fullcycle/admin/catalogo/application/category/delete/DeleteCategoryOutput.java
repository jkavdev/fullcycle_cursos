package br.com.jkavdev.fullcycle.admin.catalogo.application.category.delete;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.Category;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;

public record DeleteCategoryOutput(
        CategoryID id
) {

    public static DeleteCategoryOutput from(
            final Category aCategory
    ) {
        return new DeleteCategoryOutput(aCategory.getId());
    }
}

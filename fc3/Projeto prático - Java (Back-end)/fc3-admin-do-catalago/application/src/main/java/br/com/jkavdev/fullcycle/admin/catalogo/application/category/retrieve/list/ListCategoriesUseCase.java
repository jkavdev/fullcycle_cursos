package br.com.jkavdev.fullcycle.admin.catalogo.application.category.retrieve.list;

import br.com.jkavdev.fullcycle.admin.catalogo.application.UseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase
        extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}

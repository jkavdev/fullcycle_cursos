package br.com.jkavdev.fullcycle.catalogo.infrastructure.graphql;

import br.com.jkavdev.fullcycle.catalogo.application.category.list.ListCategoryOutput;
import br.com.jkavdev.fullcycle.catalogo.application.category.list.ListCategoryUseCase;
import br.com.jkavdev.fullcycle.catalogo.domain.category.CategorySearchQuery;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;

@Controller
public class CategoryGraphQlController {

    private final ListCategoryUseCase listCategoryUseCase;

    public CategoryGraphQlController(final ListCategoryUseCase listCategoryUseCase) {
        this.listCategoryUseCase = Objects.requireNonNull(listCategoryUseCase);
    }

    @QueryMapping
    public List<ListCategoryOutput> categories(
            @Argument final String search,
            @Argument final int page,
            @Argument final int perPage,
            @Argument final String sort,
            @Argument final String direction
    ) {
        final var aQuery =
                new CategorySearchQuery(page, perPage, search, sort, direction);

        return this.listCategoryUseCase.execute(aQuery).data();
    }
}

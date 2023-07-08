package br.com.jkavdev.fullcycle.catalogo.application.category.list;

import br.com.jkavdev.fullcycle.catalogo.domain.Fixture;
import br.com.jkavdev.fullcycle.catalogo.domain.category.CategoryGateway;
import br.com.jkavdev.fullcycle.catalogo.domain.category.CategorySearchQuery;
import br.com.jkavdev.fullcycle.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.catalogo.application.UseCaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ListCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private ListCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenValidQuery_whenCallsListCategories_shouldReturnCategories() {
        // given
        final var categories = List.of(
                Fixture.Categories.aulas(),
                Fixture.Categories.lives()
        );

        final var expectedItems = categories.stream()
                .map(ListCategoryOutput::from)
                .toList();
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "algo";
        final var expectedSort = "name";
        final var expectedDirection = "desc";
        final var expectedItemsCount = 2;

        final var aQuery =
                new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var pagination =
                new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);

        when(this.categoryGateway.findAll(any()))
                .thenReturn(pagination);

        // when
        final var actualOutput = this.useCase.execute(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.metadata().currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.metadata().perPage());
        Assertions.assertEquals(expectedItemsCount, actualOutput.metadata().total());
        Assertions.assertTrue(
                expectedItems.size() == actualOutput.data().size()
                        && expectedItems.containsAll(actualOutput.data())
        );

    }
}

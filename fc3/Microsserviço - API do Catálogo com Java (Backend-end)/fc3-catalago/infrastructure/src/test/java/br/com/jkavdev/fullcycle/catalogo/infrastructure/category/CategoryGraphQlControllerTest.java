package br.com.jkavdev.fullcycle.catalogo.infrastructure.category;

import br.com.jkavdev.fullcycle.catalogo.application.category.list.ListCategoryOutput;
import br.com.jkavdev.fullcycle.catalogo.application.category.list.ListCategoryUseCase;
import br.com.jkavdev.fullcycle.catalogo.domain.Fixture;
import br.com.jkavdev.fullcycle.catalogo.domain.category.CategorySearchQuery;
import br.com.jkavdev.fullcycle.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.catalogo.infrastructure.GraphQLControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@GraphQLControllerTest
public class CategoryGraphQlControllerTest {

    @MockBean
    private ListCategoryUseCase listCategoryUseCase;

    @Autowired
    private GraphQlTester graphql;

    @Test
    public void givenDefaultArgumentsWhenCallsListCategoriesShouldReturn() {
        // given
        final var expectedCategories = List.of(
                ListCategoryOutput.from(Fixture.Categories.aulas()),
                ListCategoryOutput.from(Fixture.Categories.lives())
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedSearch = "";

        when(this.listCategoryUseCase.execute(any()))
                .thenReturn(new Pagination<>(expectedPage, expectedPerPage, expectedCategories.size(), expectedCategories));

        final var query = """
                {
                 categories {
                  id
                  name
                 }
                }
                """;
        // when
        final var res = this.graphql.document(query).execute();

        final var actualCategories = res.path("categories")
                .entityList(ListCategoryOutput.class)
                .get();

        // then
        Assertions.assertTrue(
                expectedCategories.size() == actualCategories.size()
                        && expectedCategories.containsAll(actualCategories)
        );

        final var capturer = ArgumentCaptor.forClass(CategorySearchQuery.class);

        verify(this.listCategoryUseCase).execute(capturer.capture());

        final var actualQuery = capturer.getValue();
        Assertions.assertEquals(expectedPage, actualQuery.page());
        Assertions.assertEquals(expectedPerPage, actualQuery.perPage());
        Assertions.assertEquals(expectedSort, actualQuery.sort());
        Assertions.assertEquals(expectedDirection, actualQuery.direction());
        Assertions.assertEquals(expectedSearch, actualQuery.terms());
    }

    @Test
    public void givenCustomArgumentsWhenCallsListCategoriesShouldReturn() {
        // given
        final var expectedCategories = List.of(
                ListCategoryOutput.from(Fixture.Categories.lives()),
                ListCategoryOutput.from(Fixture.Categories.aulas())
        );

        final var expectedPage = 2;
        final var expectedPerPage = 15;
        final var expectedSort = "id";
        final var expectedDirection = "desc";
        final var expectedSearch = "asd";

        when(this.listCategoryUseCase.execute(any()))
                .thenReturn(new Pagination<>(expectedPage, expectedPerPage, expectedCategories.size(), expectedCategories));

        final var query =
        """
        {
         categories(search: "%s", page: %d, perPage: %d, sort: "%s", direction: "%s") {
          id
          name
         }
        }
        """.formatted(expectedSearch, expectedPage, expectedPerPage, expectedSort, expectedDirection);

        // when
        final var res = this.graphql.document(query).execute();

        final var actualCategories = res.path("categories")
                .entityList(ListCategoryOutput.class)
                .get();

        // then
        Assertions.assertTrue(
                actualCategories.size() == expectedCategories.size()
                        && actualCategories.containsAll(expectedCategories)
        );

        final var capturer = ArgumentCaptor.forClass(CategorySearchQuery.class);

        verify(this.listCategoryUseCase, times(1)).execute(capturer.capture());

        final var actualQuery = capturer.getValue();
        Assertions.assertEquals(expectedPage, actualQuery.page());
        Assertions.assertEquals(expectedPerPage, actualQuery.perPage());
        Assertions.assertEquals(expectedSort, actualQuery.sort());
        Assertions.assertEquals(expectedDirection, actualQuery.direction());
        Assertions.assertEquals(expectedSearch, actualQuery.terms());
    }

}

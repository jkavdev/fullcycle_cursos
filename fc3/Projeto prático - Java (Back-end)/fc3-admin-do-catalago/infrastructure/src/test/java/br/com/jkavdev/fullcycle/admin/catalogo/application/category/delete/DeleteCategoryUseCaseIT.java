package br.com.jkavdev.fullcycle.admin.catalogo.application.category.delete;

import br.com.jkavdev.fullcycle.admin.catalogo.IntegrationTest;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.Category;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@IntegrationTest
public class DeleteCategoryUseCaseIT {

    @Autowired
    private DeleteCategoryUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryGateway categoryGateway;


    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOk() {
        final var aCategory =
                Category.newCategory("Film", null, true);
        final var expectedId = aCategory.getId();

        save(aCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteCategory_shouldBeOk() {
        final var expectedId = CategoryID.from("123");

        Assertions.assertEquals(0, categoryRepository.count());

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenShouldReturnException() {
        final var expectedId = CategoryID.from("123");

        doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

    private void save(final Category... aCategory) {
        final var categories = Arrays.stream(aCategory)
                .map(CategoryJpaEntity::from)
                .toList();
        categoryRepository.saveAllAndFlush(categories);
    }
}

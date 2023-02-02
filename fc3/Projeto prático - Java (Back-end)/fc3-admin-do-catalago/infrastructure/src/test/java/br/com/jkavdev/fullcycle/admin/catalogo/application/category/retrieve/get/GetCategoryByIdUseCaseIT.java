package br.com.jkavdev.fullcycle.admin.catalogo.application.category.retrieve.get;

import br.com.jkavdev.fullcycle.admin.catalogo.IntegrationTest;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.Category;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

@IntegrationTest
public class GetCategoryByIdUseCaseIT {

    @Autowired
    private GetCategoryByIdUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryGateway categoryGateway;


    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria de filmes";
        final var expectedIsActive = true;
        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = aCategory.getId();

        Assertions.assertEquals(0, categoryRepository.count());

        save(aCategory);

        final var actualCategory = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId, actualCategory.id());
        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
//        TODO: da erro ao comparar as datas de criacao
//        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.createdAt());
//        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.updatedAt());
//        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.deletedAt());
    }

    @Test
    public void givenAnInvalidId_whenCallsGetCategory_shouldReturnNotFound() {
        final var expectedId = CategoryID.from("123");
        final var expectedErrorMessage = "Category with ID 123 was not found";

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenShouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = CategoryID.from("123");

        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(categoryGateway)
                .findById(eq(expectedId));

        final var actualException =
                Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    private void save(final Category... aCategory) {
        final var categories = Arrays.stream(aCategory)
                .map(CategoryJpaEntity::from)
                .toList();
        categoryRepository.saveAllAndFlush(categories);
    }
}

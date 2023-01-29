package br.com.jkavdev.fullcycle.admin.catalogo.domain.category;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewACategory_thenInstantiateACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Melhor categoria";
        final var expecteIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expecteIsActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expecteIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewACategoryAndValidate_thenshouldReceveError() {
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedDescription = "Melhor categoria";
        final var expecteIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expecteIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
}

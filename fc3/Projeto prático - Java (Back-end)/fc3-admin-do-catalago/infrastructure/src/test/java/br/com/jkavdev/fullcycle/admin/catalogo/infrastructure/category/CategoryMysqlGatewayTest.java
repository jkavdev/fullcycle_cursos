package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.Category;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.MysqlGatewayTest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MysqlGatewayTest
public class CategoryMysqlGatewayTest {

    @Autowired
    private CategoryMysqlGateway categoryGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAValidCategory_whenCallCreate_shouldReturnANewCategory() {
        final var expectedName = "Filmes";
        final var exceptedDescription = "Filmes";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory(expectedName, exceptedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryGateway.create(aCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(exceptedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var actualEntity =
                categoryRepository.findById(actualCategory.getId().getValue()).get();

        Assertions.assertEquals(aCategory.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(exceptedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualEntity.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_shouldReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var exceptedDescription = "Filmes";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory("Filmess", null, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        Assertions.assertEquals(1, categoryRepository.count());

        final var actualInvalidEntity =
                categoryRepository.findById(aCategory.getId().getValue()).get();

        Assertions.assertEquals("Filmess", actualInvalidEntity.getName());
        Assertions.assertNull(actualInvalidEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualInvalidEntity.isActive());

        final var aUpdatedCategory = aCategory.clone()
                .update(expectedName, exceptedDescription, expectedIsActive);

        final var actualCategory = categoryGateway.update(aUpdatedCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(exceptedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertTrue(aCategory.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var actualEntity =
                categoryRepository.findById(aCategory.getId().getValue()).get();

        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(exceptedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertTrue(aCategory.getUpdatedAt().isBefore(actualEntity.getUpdatedAt()));
        Assertions.assertEquals(aCategory.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());
    }


}




package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.Category;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.MysqlGatewayTest;
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



}




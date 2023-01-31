package br.com.jkavdev.fullcycle.admin.catalogo.application.category.update;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.Category;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

//    1 - teste do caminho feliz
//    2 - teste passando uma propriedade invalida name
//    3 - teste atualizando uma categoria inativa
//    4 - teste simulando um erro generico vindo do gateway
//    5 - teste atualizar categoria passando id invalido

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var aCategory =
                Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria de filmes";
        final var expectedIsActive = true;

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                aCategory.getId().getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));

        Mockito.verify(categoryGateway, times(1))
                .update(Mockito.argThat(anUpdatedCategory ->
                        Objects.equals(expectedName, anUpdatedCategory.getName())
                                && Objects.equals(expectedDescription, anUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, anUpdatedCategory.isActive())
                                && Objects.equals(expectedId, anUpdatedCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(), anUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(anUpdatedCategory.getUpdatedAt())
                                && Objects.isNull(anUpdatedCategory.getDeletedAt())));
    }

    @Test
    public void givenAninvalidName_whenCallsCreateCategory_shouldReturnDomainException() {
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId() {
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAnException() {
    }
}

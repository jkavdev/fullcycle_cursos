package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api;

import br.com.jkavdev.fullcycle.admin.catalogo.ControllerTest;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.create.CreateCategoryOutput;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.create.CreateCategoryUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.Category;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.Error;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.models.CreateCategoryApiInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockBean
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
//        given
        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria de filmes";
        final var expectedIsActive = true;

        final var anInput =
                new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Right(CreateCategoryOutput.from(CategoryID.from("123"))));

//        when
        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(anInput));

        final var response = this.mvc.perform(request)
                .andDo(print());

//        then
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/categories/123"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo("123")));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));

    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnNotification() throws Exception {
//      given
        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria de filmes";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";

        final var anInput =
                new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedErrorMessage))));

//        when
        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(anInput));

        final var response = this.mvc.perform(request)
                .andDo(print());

//        then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));

    }

    @Test
    public void givenAInvalidCommand_whenCallsCreateCategory_thenShouldReturnDomainException() throws Exception {
//      given
        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria de filmes";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";

        final var anInput =
                new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenThrow((DomainException.with(new Error(expectedErrorMessage))));

//        when
        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(anInput));

        final var response = this.mvc.perform(request)
                .andDo(print());

//                then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));

    }

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() throws Exception {
//        given
        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria de filmes";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = aCategory.getId().getValue();

        when(getCategoryByIdUseCase.execute(eq(expectedId)))
                .thenReturn(CategoryOutput.from(aCategory));

//        when
        final var request = get("/categories/{id}", expectedId);

        final var response = this.mvc.perform(request)
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.name", equalTo(expectedName)))
                .andExpect(jsonPath("$.description", equalTo(expectedDescription)))
                .andExpect(jsonPath("$.is_active", equalTo(expectedIsActive)))
                .andExpect(jsonPath("$.created_at", equalTo(aCategory.getCreatedAt().toString())))
                .andExpect(jsonPath("$.updated_at", equalTo(aCategory.getUpdatedAt().toString())))
                .andExpect(jsonPath("$.deleted_at", equalTo(aCategory.getDeletedAt())));

        verify(getCategoryByIdUseCase, times(1)).execute(eq(expectedId));
    }

    @Test
    public void givenAnInvalidId_whenCallsGetCategory_shouldReturnNotFound() throws Exception {
//        given
        final var expectedId = "123";
        final var expectedErrorMessage = "Category with ID 123 was not found";

        when(getCategoryByIdUseCase.execute(eq(expectedId)))
                .thenThrow(DomainException.with(
                        new Error("Category with ID %s was not found".formatted(expectedId))
                ));

//        when
        final var request = get("/categories/{id}", expectedId);

        final var response = this.mvc.perform(request)
                .andDo(print());

//        then
        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
    }

}

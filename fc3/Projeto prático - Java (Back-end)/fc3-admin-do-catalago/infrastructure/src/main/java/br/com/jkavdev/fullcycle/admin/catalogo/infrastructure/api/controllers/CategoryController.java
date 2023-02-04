package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.controllers;

import br.com.jkavdev.fullcycle.admin.catalogo.application.category.create.CreateCategoryCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.create.CreateCategoryOutput;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.create.CreateCategoryUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.models.CategoryApiOutput;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.models.CreateCategoryApiInput;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    public CategoryController(
            final CreateCategoryUseCase createCategoryUseCase,
            final GetCategoryByIdUseCase getCategoryByIdUseCase
    ) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryApiInput anInput) {
        final var aCommand = CreateCategoryCommand.with(
                anInput.name(),
                anInput.description(),
                anInput.active().booleanValue()
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity
                        .created(URI.create("/categories/" + output.id()))
                        .body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listCategories(
            String search,
            int page,
            int perPage,
            int sort,
            int dir
    ) {
        return null;
    }

    @Override
    public CategoryApiOutput getById(String id) {
        return CategoryApiPresenter.present(getCategoryByIdUseCase.execute(id));
    }

}

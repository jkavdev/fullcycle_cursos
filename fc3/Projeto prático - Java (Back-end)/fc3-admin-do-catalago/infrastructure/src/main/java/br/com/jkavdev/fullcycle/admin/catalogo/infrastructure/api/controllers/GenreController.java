package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.controllers;

import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.create.CreateGenreCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.create.CreateGenreUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.models.CreateGenreRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.models.GenreListResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.models.GenreResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.models.UpdateGenreRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.presenters.GenreApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class GenreController implements GenreAPI {

    final private CreateGenreUseCase createGenreUseCase;

    final private GetGenreByIdUseCase getGenreByIdUseCase;

    public GenreController(
            final CreateGenreUseCase createGenreUseCase,
            final GetGenreByIdUseCase getGenreByIdUseCase
    ) {
        this.createGenreUseCase = Objects.requireNonNull(createGenreUseCase);
        this.getGenreByIdUseCase = Objects.requireNonNull(getGenreByIdUseCase);
    }

    @Override
    public ResponseEntity<?> createGenre(final CreateGenreRequest anInput) {
        final var aCommand = CreateGenreCommand.with(
                anInput.name(),
                anInput.isActive(),
                anInput.categories()
        );
        final var output = createGenreUseCase.execute(aCommand);
        return ResponseEntity.created(URI.create("/genres/" + output.id())).body(output);
    }

    @Override
    public Pagination<GenreListResponse> listGenres(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return null;
    }

    @Override
    public GenreResponse getById(final String id) {
        return GenreApiPresenter.present(this.getGenreByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateGenreRequest anInput) {
        return null;
    }

    @Override
    public void deleteById(final String id) {

    }
}

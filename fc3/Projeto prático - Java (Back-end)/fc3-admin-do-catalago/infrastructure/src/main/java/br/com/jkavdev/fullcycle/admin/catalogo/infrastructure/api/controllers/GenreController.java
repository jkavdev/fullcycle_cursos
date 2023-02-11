package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.controllers;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.models.CreateGenreRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.models.GenreListResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.models.GenreResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.models.UpdateGenreRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenreController implements GenreAPI {
    @Override
    public ResponseEntity<?> createGenre(final CreateGenreRequest anInput) {
        return null;
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
        return null;
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateGenreRequest anInput) {
        return null;
    }

    @Override
    public void deleteById(final String id) {

    }
}

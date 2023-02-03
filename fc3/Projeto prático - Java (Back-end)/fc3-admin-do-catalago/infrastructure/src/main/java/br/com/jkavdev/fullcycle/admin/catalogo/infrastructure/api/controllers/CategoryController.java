package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.controllers;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements CategoryAPI {

    @Override
    public ResponseEntity<?> createCategory() {
        return null;
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
}

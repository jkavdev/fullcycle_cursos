package br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination;

public record CategorySearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}

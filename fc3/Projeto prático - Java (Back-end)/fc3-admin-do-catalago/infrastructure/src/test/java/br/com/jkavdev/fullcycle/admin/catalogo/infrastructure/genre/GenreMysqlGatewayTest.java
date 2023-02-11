package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre;

import br.com.jkavdev.fullcycle.admin.catalogo.MysqlGatewayTest;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.CategoryMysqlGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MysqlGatewayTest
public class GenreMysqlGatewayTest {

    @Autowired
    private CategoryMysqlGateway categoryGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreGateway genreGateway;

    @Test
    public void givenAValidCategory_whenCallsCreate_shouldReturnANewCategory() {
        Assertions.assertNotNull(categoryGateway);
        Assertions.assertNotNull(genreRepository);
        Assertions.assertNotNull(genreGateway);
    }

}




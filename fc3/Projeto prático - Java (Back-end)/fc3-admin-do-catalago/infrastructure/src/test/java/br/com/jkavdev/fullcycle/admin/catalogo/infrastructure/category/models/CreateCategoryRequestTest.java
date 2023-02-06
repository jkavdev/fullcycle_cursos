package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.category.models;

import br.com.jkavdev.fullcycle.admin.catalogo.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

@JacksonTest
public class CreateCategoryRequestTest {

    private JacksonTester<CreateCategoryRequest> json;

    @Autowired
    public CreateCategoryRequestTest(JacksonTester<CreateCategoryRequest> json) {
        this.json = json;
    }

    @Test
    public void testMarshall() throws IOException {
        final var expectedName = "filmes";
        final var expectedDescription = "outra categoria";
        final var expectedIsActive = true;

        final var response = new CreateCategoryRequest(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var actualJson = this.json.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.description", expectedDescription)
                .hasJsonPathValue("$.is_active", expectedIsActive)
        ;
    }

    @Test
    public void testUnmarshall() throws IOException {
        final var expectedName = "filmes";
        final var expectedDescription = "outra categoria";
        final var expectedIsActive = true;

        final var json = """
                {
                "name": "%s",
                "description": "%s",
                "is_active": "%s"
                }
                """.formatted(expectedName, expectedDescription, expectedIsActive);

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("description", expectedDescription)
                .hasFieldOrPropertyWithValue("active", expectedIsActive)
        ;
    }

}

package br.com.jkavdev.fullcycle.catalogo.application.category.delete;

import br.com.jkavdev.fullcycle.catalogo.application.UnityUseCase;
import br.com.jkavdev.fullcycle.catalogo.domain.category.CategoryGateway;

import java.util.Objects;

public class DeleteCategoryUseCase extends UnityUseCase<String> {

    private final CategoryGateway categoryGateway;

    public DeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public void execute(String anId) {
        if (anId == null) {
            return;
        }

        this.categoryGateway.deleteById(anId);
    }
}

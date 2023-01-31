package br.com.jkavdev.fullcycle.admin.catalogo.application.category.delete;

public record DeleteCategoryCommand(
        String id
) {

    public static DeleteCategoryCommand with(
            final String anId
    ) {
        return new DeleteCategoryCommand(anId);
    }
}

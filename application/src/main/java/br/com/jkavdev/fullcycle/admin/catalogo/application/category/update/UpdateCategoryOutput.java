package br.com.jkavdev.fullcycle.admin.catalogo.application.category.update;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.Category;

public record UpdateCategoryOutput(
        String id
) {

    public static UpdateCategoryOutput from(String anId) {
        return new UpdateCategoryOutput(anId);
    }

    public static UpdateCategoryOutput from(
            final Category aCategory
    ) {
        return new UpdateCategoryOutput(aCategory.getId().getValue());
    }
}

package br.com.jkavdev.fullcycle.admin.catalogo.application.genre.create;

import java.util.Collections;
import java.util.List;

public record CreateGenreCommand(
        String name,
        boolean isActive,
        List<String> categories
) {

    public List<String> categories() {
        return this.categories != null ? this.categories : Collections.emptyList();
    }

    public static CreateGenreCommand with(
            final String aName,
            final Boolean isActive,
            final List<String> categories
    ) {
        return new CreateGenreCommand(aName, (isActive != null ? isActive : true), categories);
    }
}

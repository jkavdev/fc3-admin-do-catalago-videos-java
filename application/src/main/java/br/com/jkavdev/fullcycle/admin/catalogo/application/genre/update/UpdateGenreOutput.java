package br.com.jkavdev.fullcycle.admin.catalogo.application.genre.update;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.Genre;

public record UpdateGenreOutput(
        String id
) {

    public static UpdateGenreOutput from(String anId) {
        return new UpdateGenreOutput(anId);
    }

    public static UpdateGenreOutput from(
            final Genre aGenre
    ) {
        return new UpdateGenreOutput(aGenre.getId().getValue());
    }
}

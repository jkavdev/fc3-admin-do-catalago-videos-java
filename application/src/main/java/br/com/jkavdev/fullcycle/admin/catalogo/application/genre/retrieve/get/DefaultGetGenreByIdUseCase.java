package br.com.jkavdev.fullcycle.admin.catalogo.application.genre.retrieve.get;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.exceptions.NotFoundException;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.Genre;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreID;

import java.util.Objects;

public class DefaultGetGenreByIdUseCase extends GetGenreByIdUseCase {

    private final GenreGateway genreGateway;

    public DefaultGetGenreByIdUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public GenreOutput execute(String anId) {
        final var aGenreId = GenreID.from(anId);

        return this.genreGateway.findById(aGenreId)
                .map(GenreOutput::from)
                .orElseThrow(() -> NotFoundException.with(Genre.class, aGenreId));
    }
}

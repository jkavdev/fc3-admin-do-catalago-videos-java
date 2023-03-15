package br.com.jkavdev.fullcycle.admin.catalogo.application.genre.delete;

import br.com.jkavdev.fullcycle.admin.catalogo.IntegrationTest;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.Genre;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreID;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class DeleteCastMemberUseCaseIT {

    @Autowired
    private DeleteGenreUseCase useCase;

    @Autowired
    private GenreGateway genreGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldDeleteGenre() {
        // given
        final var aGenre = genreGateway.create(Genre.newGenre("Ação", true));

        final var expectedId = aGenre.getId();

        Assertions.assertEquals(1, genreRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // when
        Assertions.assertEquals(0, genreRepository.count());
    }

    @Test
    public void givenAnInvalidGenreId_whenCallsDeleteGenre_shouldBeOk() {
        // given
        genreGateway.create(Genre.newGenre("Ação", true));

        final var expectedId = GenreID.from("123");

        Assertions.assertEquals(1, genreRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // when
        Assertions.assertEquals(1, genreRepository.count());
    }
}

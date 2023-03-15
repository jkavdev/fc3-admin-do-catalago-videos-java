package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.usecases;

import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.create.CreateGenreUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.create.DefaultCreateGenreUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.delete.DefaultDeleteGenreUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.delete.DeleteGenreUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.retrieve.get.DefaultGetGenreByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.retrieve.list.DefaultListGenresUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.retrieve.list.ListGenresUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.update.DefaultUpdateGenreUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.genre.update.UpdateGenreUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class GenreUseCaseConfiguration {

    private final GenreGateway genreGateway;

    private final CategoryGateway categoryGateway;

    public GenreUseCaseConfiguration(
            final GenreGateway genreGateway,
            final CategoryGateway categoryGateway
    ) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Bean
    public CreateGenreUseCase createGenreUseCase() {
        return new DefaultCreateGenreUseCase(categoryGateway, genreGateway);
    }

    @Bean
    public UpdateGenreUseCase updateGenreUseCase() {
        return new DefaultUpdateGenreUseCase(categoryGateway, genreGateway);
    }

    @Bean
    public DeleteGenreUseCase deleteGenreUseCase() {
        return new DefaultDeleteGenreUseCase(genreGateway);
    }

    @Bean
    public GetGenreByIdUseCase getGenreByIdUseCase() {
        return new DefaultGetGenreByIdUseCase(genreGateway);
    }

    @Bean
    public ListGenresUseCase listGenresUseCase() {
        return new DefaultListGenresUseCase(genreGateway);
    }
}

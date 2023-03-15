package br.com.jkavdev.fullcycle.admin.catalogo.application.genre.retrieve.list;

import br.com.jkavdev.fullcycle.admin.catalogo.application.UseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.SearchQuery;

public abstract class ListGenresUseCase
        extends UseCase<SearchQuery, Pagination<GenreListOutput>> {
}

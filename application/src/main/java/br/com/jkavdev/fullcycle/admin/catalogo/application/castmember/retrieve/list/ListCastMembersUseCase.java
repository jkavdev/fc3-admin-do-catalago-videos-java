package br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.list;

import br.com.jkavdev.fullcycle.admin.catalogo.application.UseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.SearchQuery;

public sealed abstract class ListCastMembersUseCase
        extends UseCase<SearchQuery, Pagination<CastMemberListOutput>>
        permits DefaultListCastMembersUseCase {
}

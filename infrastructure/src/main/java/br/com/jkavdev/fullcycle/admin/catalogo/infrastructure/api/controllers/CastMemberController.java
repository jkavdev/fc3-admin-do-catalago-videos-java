package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.controllers;

import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create.CreateCastMemberCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.delete.DeleteCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.list.ListCastMembersUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.update.UpdateCastMemberCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.update.UpdateCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.CastMemberAPI;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CastMemberListResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CreateCastMemberRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.UpdateCastMemberRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.presenters.CastMemberPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class CastMemberController implements CastMemberAPI {

    private final CreateCastMemberUseCase createCastMemberUseCase;

    private final GetCastMemberByIdUseCase getCastMemberByIdUseCase;

    private final UpdateCastMemberUseCase updateCastMemberUseCase;

    private final DeleteCastMemberUseCase deleteCastMemberUseCase;

    private final ListCastMembersUseCase listCastMembersUseCase;

    public CastMemberController(
            final CreateCastMemberUseCase createCastMemberUseCase,
            final GetCastMemberByIdUseCase getCastMemberByIdUseCase,
            final UpdateCastMemberUseCase updateCastMemberUseCase,
            final DeleteCastMemberUseCase deleteCastMemberUseCase,
            final ListCastMembersUseCase listCastMembersUseCase
    ) {
        this.createCastMemberUseCase = Objects.requireNonNull(createCastMemberUseCase);
        this.getCastMemberByIdUseCase = Objects.requireNonNull(getCastMemberByIdUseCase);
        this.updateCastMemberUseCase = Objects.requireNonNull(updateCastMemberUseCase);
        this.deleteCastMemberUseCase = Objects.requireNonNull(deleteCastMemberUseCase);
        this.listCastMembersUseCase = Objects.requireNonNull(listCastMembersUseCase);
    }

    @Override
    public ResponseEntity<?> create(CreateCastMemberRequest anInput) {
        final var aCommand = CreateCastMemberCommand.with(anInput.name(), anInput.type());

        final var output = createCastMemberUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/cast_members/" + output.id())).body(output);
    }

    @Override
    public CastMemberResponse getById(String id) {
        return CastMemberPresenter.present(getCastMemberByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(String id, UpdateCastMemberRequest anInput) {
        final var aCommand =
                UpdateCastMemberCommand.with(id, anInput.name(), anInput.type());

        final var output = updateCastMemberUseCase.execute(aCommand);

        return ResponseEntity.ok((output));
    }

    @Override
    public void deleteById(String id) {
        deleteCastMemberUseCase.execute(id);
    }

    @Override
    public Pagination<CastMemberListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return this.listCastMembersUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(CastMemberPresenter::present);
    }
}

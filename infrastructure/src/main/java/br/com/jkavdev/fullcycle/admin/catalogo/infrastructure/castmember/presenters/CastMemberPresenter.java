package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.presenters;

import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.get.CastMemberOutput;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.list.CastMemberListOutput;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CastMemberListResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;

public interface CastMemberPresenter {

    static CastMemberResponse present(final CastMemberOutput output) {
        return new CastMemberResponse(
                output.id(),
                output.name(),
                output.type().name(),
                output.createdAt().toString(),
                output.updatedAt().toString()
        );
    }

    static CastMemberListResponse present(final CastMemberListOutput output) {
        return new CastMemberListResponse(
                output.id(),
                output.name(),
                output.type().name(),
                output.createdAt().toString()
        );
    }

}

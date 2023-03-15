package br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.update;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMember;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberID;

public record UpdateCastMemberOutput(
        String id
) {

    public static UpdateCastMemberOutput from(String anId) {
        return new UpdateCastMemberOutput(anId);
    }

    public static UpdateCastMemberOutput from(
            final CastMember aMember
    ) {
        return from(aMember.getId());
    }

    public static UpdateCastMemberOutput from(final CastMemberID anId) {
        return from(anId.getValue());
    }
}

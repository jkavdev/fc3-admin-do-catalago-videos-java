package br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.update;

import br.com.jkavdev.fullcycle.admin.catalogo.application.UseCase;

public sealed abstract class UpdateCastMemberUseCase
        extends UseCase<UpdateCastMemberCommand, UpdateCastMemberOutput>
        permits DefaultUpdateCastMemberUseCase {
}

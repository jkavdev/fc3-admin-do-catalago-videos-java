package br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create;

import br.com.jkavdev.fullcycle.admin.catalogo.application.UseCase;

public sealed abstract class CreateCastMemberUseCase
        extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput>
        permits DefaultCreateCastMemberUseCase {
}

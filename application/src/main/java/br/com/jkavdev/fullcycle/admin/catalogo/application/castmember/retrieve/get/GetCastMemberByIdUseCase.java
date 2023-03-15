package br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.get;

import br.com.jkavdev.fullcycle.admin.catalogo.application.UseCase;

public sealed abstract class GetCastMemberByIdUseCase
        extends UseCase<String, CastMemberOutput>
        permits DefaultGetCastMemberByIdUseCase {
}

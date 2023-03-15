package br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.delete;

import br.com.jkavdev.fullcycle.admin.catalogo.application.UnityUseCase;

public sealed abstract class DeleteCastMemberUseCase
        extends UnityUseCase<String>
        permits DefaultDeleteCastMemberUseCase {
}

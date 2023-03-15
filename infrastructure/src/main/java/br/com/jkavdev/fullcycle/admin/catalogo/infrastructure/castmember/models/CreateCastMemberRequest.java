package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberType;

public record CreateCastMemberRequest(String name, CastMemberType type) {
}

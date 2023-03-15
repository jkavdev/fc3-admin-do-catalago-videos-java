package br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.Identifier;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;

public class CastMemberID extends Identifier {

    private final String value;

    private CastMemberID(final String id) {
        this.value = Objects.requireNonNull(id);
    }

    public static CastMemberID unique() {
        return CastMemberID.from(IdUtils.uuid());
    }

    public static CastMemberID from(final String anId) {
        return new CastMemberID(anId);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CastMemberID that = (CastMemberID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

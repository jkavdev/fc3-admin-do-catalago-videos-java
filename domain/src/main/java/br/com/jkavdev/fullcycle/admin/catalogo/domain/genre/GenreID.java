package br.com.jkavdev.fullcycle.admin.catalogo.domain.genre;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.Identifier;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;

public class GenreID extends Identifier {

    private final String value;

    private GenreID(final String id) {
        Objects.requireNonNull(id);
        this.value = id;
    }

    public static GenreID unique() {
        return GenreID.from(IdUtils.uuid());
    }

    public static GenreID from(final String anId) {
        return new GenreID(anId);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GenreID that = (GenreID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

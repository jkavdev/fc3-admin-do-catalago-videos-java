package br.com.jkavdev.fullcycle.admin.catalogo.domain.category;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.Identifier;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;

public class CategoryID extends Identifier {

    private final String value;

    private CategoryID(final String id) {
        this.value = Objects.requireNonNull(id);
    }

    public static CategoryID unique() {
        return CategoryID.from(IdUtils.uuid());
    }

    public static CategoryID from(final String anId) {
        return new CategoryID(anId);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryID that = (CategoryID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

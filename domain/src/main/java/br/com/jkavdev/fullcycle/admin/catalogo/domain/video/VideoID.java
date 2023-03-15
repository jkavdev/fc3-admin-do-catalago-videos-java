package br.com.jkavdev.fullcycle.admin.catalogo.domain.video;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.Identifier;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;

public class VideoID extends Identifier {

    private final String value;

    private VideoID(final String id) {
        this.value = Objects.requireNonNull(id);
    }

    public static VideoID from(final String anId) {
        return new VideoID(anId.toLowerCase());
    }

    public static VideoID unique() {
        return VideoID.from(IdUtils.uuid());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VideoID that = (VideoID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

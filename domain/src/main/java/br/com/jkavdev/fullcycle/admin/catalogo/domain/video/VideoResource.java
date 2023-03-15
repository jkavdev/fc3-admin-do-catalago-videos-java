package br.com.jkavdev.fullcycle.admin.catalogo.domain.video;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.ValueObject;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.resource.Resource;

import java.util.Objects;

public class VideoResource extends ValueObject {

    private final Resource resource;

    private final VideoMediaType type;

    public VideoResource(final Resource resource, final VideoMediaType type) {
        this.resource = Objects.requireNonNull(resource);
        this.type = Objects.requireNonNull(type);
    }

    public static VideoResource with(final Resource aResource, final VideoMediaType aType) {
        return new VideoResource(aResource, aType);
    }

    public Resource resource() {
        return resource;
    }

    public VideoMediaType type() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VideoResource that = (VideoResource) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}

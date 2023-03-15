package br.com.jkavdev.fullcycle.admin.catalogo.domain.resource;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.ValueObject;

import java.util.Arrays;
import java.util.Objects;

public class Resource extends ValueObject {

    private final String checksum;
    private final byte[] content;

    private final String contentType;

    private final String name;

    private Resource(
            final String checksum,
            final byte[] content,
            final String contentType,
            final String name
    ) {
        this.checksum = Objects.requireNonNull(checksum);
        this.content = Objects.requireNonNull(content);
        this.contentType = Objects.requireNonNull(contentType);
        this.name = Objects.requireNonNull(name);
    }

    public static Resource with(
            final String checksum,
            final byte[] content,
            final String contentType,
            final String name
    ) {
        return new Resource(checksum, content, contentType, name);
    }

    public String checksum() {
        return checksum;
    }

    public byte[] content() {
        return content;
    }

    public String contentType() {
        return contentType;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Resource resource = (Resource) o;
        return Objects.equals(checksum, resource.checksum)
                && Arrays.equals(content, resource.content)
                && Objects.equals(contentType, resource.contentType)
                && Objects.equals(name, resource.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(checksum, contentType, name);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
}

package br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.get;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.resource.Resource;

public record MediaOutput(
        String name,
        String contentType,
        byte[] content
) {
    public static MediaOutput with(final Resource aResource) {
        return new MediaOutput(
                aResource.name(),
                aResource.contentType(),
                aResource.content()
        );
    }
}

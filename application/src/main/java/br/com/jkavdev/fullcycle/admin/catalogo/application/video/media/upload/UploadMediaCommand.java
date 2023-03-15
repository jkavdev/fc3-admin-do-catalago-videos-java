package br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.upload;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoResource;

public record UploadMediaCommand(
        String videoId,
        VideoResource videoResource
) {

    public static UploadMediaCommand with(final String anId, final VideoResource aResource) {
        return new UploadMediaCommand(anId, aResource);
    }
}

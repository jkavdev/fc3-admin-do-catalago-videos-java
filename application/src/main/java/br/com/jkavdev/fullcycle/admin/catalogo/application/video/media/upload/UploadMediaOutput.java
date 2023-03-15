package br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.upload;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Video;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoMediaType;

public record UploadMediaOutput(
        String videoId,
        VideoMediaType mediaType
) {
    public static UploadMediaOutput with(final Video aVideo, final VideoMediaType aType) {
        return new UploadMediaOutput(
                aVideo.getId().getValue(),
                aType
        );
    }
}

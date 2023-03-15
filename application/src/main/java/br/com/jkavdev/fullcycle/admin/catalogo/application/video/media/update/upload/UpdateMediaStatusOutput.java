package br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.update.upload;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Video;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoMediaType;

public record UpdateMediaStatusOutput(
        String videoId,
        VideoMediaType mediaType
) {
    public static UpdateMediaStatusOutput with(final Video aVideo, final VideoMediaType aType) {
        return new UpdateMediaStatusOutput(
                aVideo.getId().getValue(),
                aType
        );
    }
}

package br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.get;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.exceptions.NotFoundException;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.Error;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.MediaResourceGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoMediaType;

import java.util.Objects;

public class DefaultGetMediaUseCase extends GetMediaUseCase {

    private final MediaResourceGateway mediaResourceGateway;

    public DefaultGetMediaUseCase(final MediaResourceGateway mediaResourceGateway) {
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Override
    public MediaOutput execute(final GetMediaCommand aCmd) {
        final var anId = VideoID.from(aCmd.videoId());
        final var aType = VideoMediaType.of(aCmd.mediaType())
                .orElseThrow(() -> typeNotFound(aCmd.mediaType()));

        final var aResource =
                this.mediaResourceGateway.getResource(anId, aType)
                        .orElseThrow(() -> notFound(aCmd.videoId(), aCmd.mediaType()));


        return MediaOutput.with(aResource);
    }

    private NotFoundException notFound(final String anId, final String aType) {
        return NotFoundException.with(new Error("resource %s not found for video %s".formatted(aType, anId)));
    }

    private NotFoundException typeNotFound(final String aType) {
        return NotFoundException.with(new Error("media type %s doesn't exists".formatted(aType)));
    }
}

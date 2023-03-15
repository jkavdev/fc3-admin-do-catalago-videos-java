package br.com.jkavdev.fullcycle.admin.catalogo.application.video.delete;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.MediaResourceGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoID;

import java.util.Objects;

public class DefaultDeleteVideoUseCase extends DeleteVideoUseCase {

    private final VideoGateway videoGateway;

    private final MediaResourceGateway mediaResourceGateway;

    public DefaultDeleteVideoUseCase(
            final VideoGateway videoGateway,
            final MediaResourceGateway mediaResourceGateway
    ) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Override
    public void execute(String anId) {
        final var aVideoId = VideoID.from(anId);
        this.videoGateway.deleteById(aVideoId);
        this.mediaResourceGateway.clearResources(aVideoId);
    }
}

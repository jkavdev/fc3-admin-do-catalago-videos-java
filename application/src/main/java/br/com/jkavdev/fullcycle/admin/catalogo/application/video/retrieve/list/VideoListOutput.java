package br.com.jkavdev.fullcycle.admin.catalogo.application.video.retrieve.list;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Video;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoPreview;

import java.time.Instant;

// TODO: retornar na listagem de videos, os nomes das categorias, generos e membros

public record VideoListOutput(
        String id,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt
) {

    public static VideoListOutput from(
            final Video aVideo
    ) {
        return new VideoListOutput(
                aVideo.getId().getValue(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt()
        );
    }

    public static VideoListOutput from(final VideoPreview aVideo) {
        return new VideoListOutput(
                aVideo.id(),
                aVideo.title(),
                aVideo.description(),
                aVideo.createdAt(),
                aVideo.updatedAt()
        );
    }
}

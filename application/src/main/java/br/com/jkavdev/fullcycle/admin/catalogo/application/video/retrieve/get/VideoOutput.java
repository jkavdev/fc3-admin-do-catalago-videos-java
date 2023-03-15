package br.com.jkavdev.fullcycle.admin.catalogo.application.video.retrieve.get;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.Identifier;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.AudioVideoMedia;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.ImageMedia;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Rating;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Video;

import java.time.Instant;
import java.util.Set;

import static br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.CollectionUtils.mapTo;

public record VideoOutput(
        String id,
        String title,
        String description,
        int launchedAt,
        double duration,
        boolean opened,
        boolean published,
        Rating rating,
        Set<String> categories,
        Set<String> genres,
        Set<String> members,
        AudioVideoMedia video,
        AudioVideoMedia trailer,
        ImageMedia banner,
        ImageMedia thumbnail,
        ImageMedia thumbnailHalf,
        Instant createdAt,
        Instant updatedAt
) {

    public static VideoOutput from(final Video aVideo) {
        return new VideoOutput(
                aVideo.getId().getValue(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getLaunchedAt().getValue(),
                aVideo.getDuration(),
                aVideo.getOpened(),
                aVideo.getPublished(),
                aVideo.getRating(),
                mapTo(aVideo.getCategories(), Identifier::getValue),
                mapTo(aVideo.getGenres(), Identifier::getValue),
                mapTo(aVideo.getCastMembers(), Identifier::getValue),
                aVideo.getVideo().orElse(null),
                aVideo.getTrailer().orElse(null),
                aVideo.getBanner().orElse(null),
                aVideo.getThumbnail().orElse(null),
                aVideo.getThumbnailHalf().orElse(null),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt()
        );
    }
}

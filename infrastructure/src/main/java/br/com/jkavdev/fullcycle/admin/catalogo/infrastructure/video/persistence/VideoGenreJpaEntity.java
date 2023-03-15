package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreID;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "VideoGenre")
@Table(name = "videos_genres")
public class VideoGenreJpaEntity {

    @EmbeddedId
    private VideoGenreID id;

    @ManyToOne(fetch = LAZY)
    @MapsId("videoId")
    private VideoJpaEntity video;

    private VideoGenreJpaEntity() {
    }

    public VideoGenreJpaEntity(final VideoGenreID id, final VideoJpaEntity video) {
        this.id = id;
        this.video = video;
    }

    public static VideoGenreJpaEntity from(final VideoJpaEntity video, final GenreID genre) {
        return new VideoGenreJpaEntity(
                VideoGenreID.from(video.getId(), genre.getValue()),
                video
        );
    }

    public VideoGenreID getId() {
        return id;
    }

    public void setId(VideoGenreID id) {
        this.id = id;
    }

    public VideoJpaEntity getVideo() {
        return video;
    }

    public void setVideo(VideoJpaEntity video) {
        this.video = video;
    }
}

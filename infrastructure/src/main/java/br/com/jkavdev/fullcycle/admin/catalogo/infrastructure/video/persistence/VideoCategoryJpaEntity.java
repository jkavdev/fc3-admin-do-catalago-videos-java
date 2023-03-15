package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "VideoCategory")
@Table(name = "videos_categories")
public class VideoCategoryJpaEntity {

    @EmbeddedId
    private VideoCategoryID id;

    @ManyToOne(fetch = LAZY)
    @MapsId("videoId")
    private VideoJpaEntity video;

    private VideoCategoryJpaEntity() {
    }

    public VideoCategoryJpaEntity(final VideoCategoryID id, final VideoJpaEntity video) {
        this.id = id;
        this.video = video;
    }

    public static VideoCategoryJpaEntity from(final VideoJpaEntity video, final CategoryID category) {
        return new VideoCategoryJpaEntity(
                VideoCategoryID.from(video.getId(), category.getValue()),
                video
        );
    }

    public VideoCategoryID getId() {
        return id;
    }

    public void setId(VideoCategoryID id) {
        this.id = id;
    }

    public VideoJpaEntity getVideo() {
        return video;
    }

    public void setVideo(VideoJpaEntity video) {
        this.video = video;
    }
}

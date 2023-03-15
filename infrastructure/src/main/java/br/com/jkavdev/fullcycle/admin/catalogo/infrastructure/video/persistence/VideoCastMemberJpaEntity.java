package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberID;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "VideoCastMember")
@Table(name = "videos_cast_members")
public class VideoCastMemberJpaEntity {

    @EmbeddedId
    private VideoCastMemberID id;

    @ManyToOne(fetch = LAZY)
    @MapsId("videoId")
    private VideoJpaEntity video;

    private VideoCastMemberJpaEntity() {
    }

    public VideoCastMemberJpaEntity(final VideoCastMemberID id, final VideoJpaEntity video) {
        this.id = id;
        this.video = video;
    }

    public static VideoCastMemberJpaEntity from(final VideoJpaEntity video, final CastMemberID castMember) {
        return new VideoCastMemberJpaEntity(
                VideoCastMemberID.from(video.getId(), castMember.getValue()),
                video
        );
    }

    public VideoCastMemberID getId() {
        return id;
    }

    public void setId(VideoCastMemberID id) {
        this.id = id;
    }

    public VideoJpaEntity getVideo() {
        return video;
    }

    public void setVideo(VideoJpaEntity video) {
        this.video = video;
    }
}

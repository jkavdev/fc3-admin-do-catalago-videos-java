package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.ImageMedia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ImageMedia")
@Table(name = "videos_image_media")
public class ImageMediaJpaEntity {

    @Id
    private String id;

    @Column(name = "checksum", nullable = false)
    private String checksum;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    private ImageMediaJpaEntity() {
    }

    private ImageMediaJpaEntity(
            final String id,
            final String checksum,
            final String name,
            final String filePath
    ) {
        this.id = id;
        this.checksum = checksum;
        this.name = name;
        this.filePath = filePath;
    }

    public static ImageMediaJpaEntity from(final ImageMedia media) {
        return new ImageMediaJpaEntity(
                media.id(),
                media.checksum(),
                media.name(),
                media.location()
        );
    }

    public ImageMedia toDomain() {
        return ImageMedia.with(
                getId(),
                getChecksum(),
                getName(),
                getFilePath()
        );
    }

    public String getId() {
        return id;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }
}

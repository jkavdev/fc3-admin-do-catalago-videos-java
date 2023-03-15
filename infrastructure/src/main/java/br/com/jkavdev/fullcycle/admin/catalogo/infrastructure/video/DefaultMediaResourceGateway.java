package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.resource.Resource;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.*;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.properties.StorageProperties;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.StorageService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultMediaResourceGateway implements MediaResourceGateway {

    private final String fileNamePattern;
    private final String locationPattern;
    private final StorageService storageService;

    public DefaultMediaResourceGateway(final StorageProperties props, final StorageService storageService) {
        this.fileNamePattern = props.fileNamePattern();
        this.locationPattern = props.locationPattern();
        this.storageService = storageService;
    }

    @Override
    public AudioVideoMedia storeAudioVideo(final VideoID anId, final VideoResource videoResource) {
        final var filePath = this.filePath(anId, videoResource.type());
        final var aResource = videoResource.resource();
        store(filePath, aResource);
        return AudioVideoMedia.with(aResource.checksum(), aResource.name(), filePath);
    }

    @Override
    public Optional<Resource> getResource(final VideoID anId, final VideoMediaType type) {
        return this.storageService.get(filePath(anId, type));
    }

    @Override
    public ImageMedia storeImage(final VideoID anId, final VideoResource imageResource) {
        final var filePath = this.filePath(anId, imageResource.type());
        final var aResource = imageResource.resource();
        store(filePath, aResource);
        return ImageMedia.with(aResource.checksum(), aResource.name(), filePath);
    }

    @Override
    public void clearResources(VideoID anId) {
        final var ids = this.storageService.list(folder(anId));
        this.storageService.deleteAll(ids);
    }

    private String fileName(final VideoMediaType aType) {
        return fileNamePattern.replace("{type}", aType.name());
    }

    private String folder(final VideoID anId) {
        return locationPattern.replace("{videoId}", anId.getValue());
    }

    private String filePath(final VideoID anId, final VideoMediaType type) {
        return folder(anId)
                .concat("/")
                .concat(fileName(type));
    }

    private void store(final String filePath, final Resource aResource) {
        storageService.store(filePath, aResource);
    }
}

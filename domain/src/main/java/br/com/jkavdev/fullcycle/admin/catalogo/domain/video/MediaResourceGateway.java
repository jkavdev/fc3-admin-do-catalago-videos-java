package br.com.jkavdev.fullcycle.admin.catalogo.domain.video;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.resource.Resource;

import java.util.Optional;

public interface MediaResourceGateway {

    AudioVideoMedia storeAudioVideo(VideoID anId, VideoResource aResource);

    Optional<Resource> getResource(VideoID anId, VideoMediaType type);

    ImageMedia storeImage(VideoID anId, VideoResource aResource);

    void clearResources(VideoID anId);

}

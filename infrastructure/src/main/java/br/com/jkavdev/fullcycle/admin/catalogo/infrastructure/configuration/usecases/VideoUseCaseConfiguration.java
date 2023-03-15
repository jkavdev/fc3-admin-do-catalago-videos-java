package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.usecases;

import br.com.jkavdev.fullcycle.admin.catalogo.application.video.create.CreateVideoUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.create.DefaultCreateVideoUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.delete.DefaultDeleteVideoUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.delete.DeleteVideoUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.get.DefaultGetMediaUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.get.GetMediaUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.update.upload.DefaultUpdateMediaStatusUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.update.upload.UpdateMediaStatusUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.upload.DefaultUploadMediaUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.upload.UploadMediaUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.retrieve.get.DefaultGetVideoByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.retrieve.get.GetVideoByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.retrieve.list.DefaultListVideosUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.retrieve.list.ListVideosUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.update.DefaultUpdateVideoUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.update.UpdateVideoUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.MediaResourceGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class VideoUseCaseConfiguration {

    private final CategoryGateway categoryGateway;

    private final GenreGateway genreGateway;

    private final CastMemberGateway castMemberGateway;

    private final VideoGateway videoGateway;

    private final MediaResourceGateway mediaResourceGateway;

    public VideoUseCaseConfiguration(
            final CategoryGateway categoryGateway,
            final GenreGateway genreGateway,
            final CastMemberGateway castMemberGateway,
            final VideoGateway videoGateway,
            final MediaResourceGateway mediaResourceGateway
    ) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
        this.videoGateway = Objects.requireNonNull(videoGateway);
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Bean
    public CreateVideoUseCase createVideoUseCase() {
        return new DefaultCreateVideoUseCase(categoryGateway, genreGateway, castMemberGateway, videoGateway, mediaResourceGateway);
    }

    @Bean
    public UpdateVideoUseCase updateVideoUseCase() {
        return new DefaultUpdateVideoUseCase(categoryGateway, genreGateway, castMemberGateway, videoGateway, mediaResourceGateway);
    }

    @Bean
    public GetVideoByIdUseCase getVideoByIdUseCase() {
        return new DefaultGetVideoByIdUseCase(videoGateway);
    }

    @Bean
    public DeleteVideoUseCase deleteVideoUseCase() {
        return new DefaultDeleteVideoUseCase(videoGateway, mediaResourceGateway);
    }

    @Bean
    public ListVideosUseCase listVideosUseCase() {
        return new DefaultListVideosUseCase(videoGateway);
    }

    @Bean
    public GetMediaUseCase getMediaUseCase() {
        return new DefaultGetMediaUseCase(mediaResourceGateway);
    }

    @Bean
    public UploadMediaUseCase uploadMediaUseCase() {
        return new DefaultUploadMediaUseCase(mediaResourceGateway, videoGateway);
    }

    @Bean
    public UpdateMediaStatusUseCase updateMediaStatusUseCase() {
        return new DefaultUpdateMediaStatusUseCase(videoGateway);
    }
}

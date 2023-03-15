package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.controllers;

import br.com.jkavdev.fullcycle.admin.catalogo.application.video.create.CreateVideoCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.create.CreateVideoUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.delete.DeleteVideoUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.get.GetMediaCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.get.GetMediaUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.upload.UploadMediaCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.upload.UploadMediaUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.retrieve.get.GetVideoByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.retrieve.list.ListVideosUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.update.UpdateVideoCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.update.UpdateVideoUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.exceptions.NotificationException;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.resource.Resource;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.Error;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoMediaType;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoResource;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoSearchQuery;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.VideoAPI;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.utils.HashingUtils;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.CreateVideoRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.UpdateVideoRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.VideoListResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.VideoResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.presenters.VideoApiPresenter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

import static br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.CollectionUtils.mapTo;

@RestController
public class VideoController implements VideoAPI {

    private final CreateVideoUseCase createVideoUseCase;

    private final GetVideoByIdUseCase getVideoByIdUseCase;

    private final UpdateVideoUseCase updateVideoUseCase;

    private final DeleteVideoUseCase deleteVideoUseCase;

    private final ListVideosUseCase listVideosUseCase;

    private final GetMediaUseCase getMediaUseCase;

    private final UploadMediaUseCase uploadMediaUseCase;

    public VideoController(
            final CreateVideoUseCase createVideoUseCase,
            final GetVideoByIdUseCase getVideoByIdUseCase,
            final UpdateVideoUseCase updateVideoUseCase,
            final DeleteVideoUseCase deleteVideoUseCase,
            final ListVideosUseCase listVideosUseCase,
            final GetMediaUseCase getMediaUseCase,
            final UploadMediaUseCase uploadMediaUseCase
    ) {
        this.createVideoUseCase = Objects.requireNonNull(createVideoUseCase);
        this.getVideoByIdUseCase = Objects.requireNonNull(getVideoByIdUseCase);
        this.updateVideoUseCase = Objects.requireNonNull(updateVideoUseCase);
        this.deleteVideoUseCase = Objects.requireNonNull(deleteVideoUseCase);
        this.listVideosUseCase = Objects.requireNonNull(listVideosUseCase);
        this.getMediaUseCase = Objects.requireNonNull(getMediaUseCase);
        this.uploadMediaUseCase = Objects.requireNonNull(uploadMediaUseCase);
    }

    @Override
    public ResponseEntity<?> createFull(
            String title,
            final String description,
            final Integer yearLaunched,
            final Double duration,
            final Boolean opened,
            final Boolean published,
            final String rating,
            final Set<String> castMembers,
            final Set<String> categories,
            final Set<String> genres,
            final MultipartFile videoFile,
            final MultipartFile trailerFile,
            final MultipartFile bannerFile,
            final MultipartFile thumbFile,
            final MultipartFile thumbHalfFile
    ) {
        final var aCmd = CreateVideoCommand.with(
                title,
                description,
                yearLaunched,
                duration,
                opened,
                published,
                rating,
                categories,
                genres,
                castMembers,
                resourceOf(videoFile),
                resourceOf(trailerFile),
                resourceOf(bannerFile),
                resourceOf(thumbFile),
                resourceOf(thumbHalfFile)
        );

        final var output = this.createVideoUseCase.execute(aCmd);

        return ResponseEntity.created(URI.create("/videos/" + output.id())).body(output);
    }

    @Override
    public ResponseEntity<?> createPartial(final CreateVideoRequest payload) {
        final var aCmd = CreateVideoCommand.with(
                payload.title(),
                payload.description(),
                payload.yearLaunched(),
                payload.duration(),
                payload.opened(),
                payload.published(),
                payload.rating(),
                payload.categories(),
                payload.genres(),
                payload.castMembers()
        );

        final var output = this.createVideoUseCase.execute(aCmd);

        return ResponseEntity.created(URI.create("/videos/" + output.id())).body(output);
    }

    @Override
    public VideoResponse getById(final String anId) {
        return VideoApiPresenter.present(this.getVideoByIdUseCase.execute(anId));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateVideoRequest payload) {
        final var aCmd = UpdateVideoCommand.with(
                id,
                payload.title(),
                payload.description(),
                payload.yearLaunched(),
                payload.duration(),
                payload.opened(),
                payload.published(),
                payload.rating(),
                payload.categories(),
                payload.genres(),
                payload.castMembers()
        );

        final var output = this.updateVideoUseCase.execute(aCmd);

        return ResponseEntity.ok()
                .location(URI.create("/videos/" + output.id()))
                .body(VideoApiPresenter.present(output));
    }

    @Override
    public void deleteById(final String id) {
        this.deleteVideoUseCase.execute(id);
    }

    @Override
    public Pagination<VideoListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction,
            final Set<String> categories,
            final Set<String> castMembers,
            final Set<String> genres
    ) {

        final var categoriesIds = mapTo(categories, CategoryID::from);
        final var genresIds = mapTo(genres, GenreID::from);
        final var castMembersIds = mapTo(castMembers, CastMemberID::from);

        final var aQuery = new VideoSearchQuery(page, perPage, search, sort, direction, categoriesIds, genresIds, castMembersIds);

        return VideoApiPresenter.present(this.listVideosUseCase.execute(aQuery));
    }

    @Override
    public ResponseEntity<byte[]> getMediaByType(final String id, final String type) {
        final var aMedia =
                this.getMediaUseCase.execute(GetMediaCommand.with(id, type));

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(aMedia.contentType()))
                .contentLength(aMedia.content().length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=%s".formatted(aMedia.name()))
                .body(aMedia.content());
    }

    @Override
    public ResponseEntity<?> uploadMediaByType(
            final String id,
            final String type,
            final MultipartFile media
    ) {
        final var aType = VideoMediaType.of(type)
                .orElseThrow(() -> NotificationException.with(new Error("invalid %s for VideoMediaType".formatted(type))));

        final var aCmd = UploadMediaCommand.with(id, VideoResource.with(resourceOf(media), aType));

        final var output = this.uploadMediaUseCase.execute(aCmd);

        return ResponseEntity
                .created(URI.create("/videos/%s/medias/%s".formatted(id, type)))
                .body(VideoApiPresenter.present(output));
    }

    private Resource resourceOf(final MultipartFile part) {
        if (part == null) {
            return null;
        }

        try {
            return Resource.with(
                    HashingUtils.checksum(part.getBytes()),
                    part.getBytes(),
                    part.getContentType(),
                    part.getOriginalFilename()
            );
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}

package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.CreateVideoRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.UpdateVideoRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.VideoListResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.VideoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RequestMapping("videos")
@Tag(name = "Video")
public interface VideoAPI {

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new video with medias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> createFull(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "year_launched", required = false) Integer yearLaunched,
            @RequestParam(value = "duration", required = false) Double duration,
            @RequestParam(value = "opened", required = false) Boolean opened,
            @RequestParam(value = "published", required = false) Boolean published,
            @RequestParam(value = "rating", required = false) String rating,
            @RequestParam(value = "cast_members_id", required = false) Set<String> castMembers,
            @RequestParam(value = "categories_id", required = false) Set<String> categories,
            @RequestParam(value = "genres_id", required = false) Set<String> genres,
            @RequestParam(value = "video_file", required = false) MultipartFile videoFile,
            @RequestParam(value = "trailer_file", required = false) MultipartFile trailerFile,
            @RequestParam(value = "banner_file", required = false) MultipartFile bannerFile,
            @RequestParam(value = "thumb_file", required = false) MultipartFile thumbFile,
            @RequestParam(value = "thumb_half_file", required = false) MultipartFile thumbHalfFile
    );

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new video without medias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> createPartial(@RequestBody CreateVideoRequest payload);

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a video by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Video retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Video was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    VideoResponse getById(@PathVariable(name = "id") final String id);

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a video by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Video updated successfully"),
            @ApiResponse(responseCode = "404", description = "Video was not found"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> updateById(
            @PathVariable(name = "id") final String id,
            @RequestBody final UpdateVideoRequest payload
    );

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a video by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Video deleted"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    void deleteById(@PathVariable(name = "id") String id);

    @GetMapping
    @Operation(summary = "List all videos paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "A query param was invalid"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<VideoListResponse> list(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "25") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "title") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction,
            @RequestParam(name = "categories_id", required = false, defaultValue = "") Set<String> categories,
            @RequestParam(name = "cast_members_id", required = false, defaultValue = "") Set<String> castMembers,
            @RequestParam(name = "genres_id", required = false, defaultValue = "") Set<String> genres
    );

    @GetMapping(
            value = "{id}/medias/{type}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a media by it's type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Media retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Video was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<byte[]> getMediaByType(
            @PathVariable(name = "id") final String id,
            @PathVariable(name = "type") final String type
    );

    @PostMapping("{id}/medias/{type}")
    @Operation(summary = "upload a video media by it's type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Media created successfully"),
            @ApiResponse(responseCode = "404", description = "Video was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> uploadMediaByType(
            @PathVariable(name = "id") final String id,
            @PathVariable(name = "type") final String type,
            @RequestParam(name = "media_file") MultipartFile media
    );
}

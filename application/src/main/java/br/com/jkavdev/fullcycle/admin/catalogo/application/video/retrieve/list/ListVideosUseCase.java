package br.com.jkavdev.fullcycle.admin.catalogo.application.video.retrieve.list;

import br.com.jkavdev.fullcycle.admin.catalogo.application.UseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoSearchQuery;

public abstract class ListVideosUseCase
        extends UseCase<VideoSearchQuery, Pagination<VideoListOutput>> {
}

package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoPreview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface VideoRepository extends JpaRepository<VideoJpaEntity, String> {

    @Query("""
            select new br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoPreview(
              v.id,
              v.title,
              v.description,
              v.createdAt,
              v.updatedAt
            )
            from Video v
            join v.castMembers members
            join v.categories categories
            join v.genres genres
            where
                ( :terms is null or upper(v.title) like :terms )
                and
                ( :castMembers is null or members.id.castMemberId in :castMembers )
                and
                ( :categories is null or categories.id.categoryId in :categories )
                and
                ( :genres is null or genres.id.genreId in :genres )
            """)
    Page<VideoPreview> findAll(
            @Param("terms") String terms,
            @Param("castMembers") Set<String> castMembers,
            @Param("categories") Set<String> categories,
            @Param("genres") Set<String> genres,
            Pageable page
    );
}

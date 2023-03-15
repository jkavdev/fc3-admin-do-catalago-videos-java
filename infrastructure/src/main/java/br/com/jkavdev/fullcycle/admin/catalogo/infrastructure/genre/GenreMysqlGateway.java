package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.Genre;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.persistence.GenreJpaEntity;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.persistence.GenreRepository;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class GenreMysqlGateway implements GenreGateway {

    final private GenreRepository genreRepository;

    public GenreMysqlGateway(final GenreRepository genreRepository) {
        this.genreRepository = Objects.requireNonNull(genreRepository);
    }

    @Override
    public Genre create(final Genre aGenre) {
        return save(aGenre);
    }

    @Override
    public void deleteById(GenreID anId) {
        final var anIdValue = anId.getValue();
        if (genreRepository.existsById(anIdValue)) {
            genreRepository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<Genre> findById(GenreID anId) {
        return genreRepository.findById(anId.getValue())
                .map(GenreJpaEntity::toAggregate);
    }

    @Override
    public Genre update(final Genre aGenre) {
        return save(aGenre);
    }

    @Override
    public Pagination<Genre> findAll(SearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(ter -> !ter.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult =
                this.genreRepository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(GenreJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public List<GenreID> existsByIds(Iterable<GenreID> genresIDs) {
        final var ids = StreamSupport.stream(genresIDs.spliterator(), false)
                .map(GenreID::getValue)
                .toList();
        return genreRepository.existsByIds(ids).stream()
                .map(GenreID::from)
                .toList();
    }

    private Genre save(Genre aGenre) {
        return this.genreRepository.save(GenreJpaEntity.from(aGenre))
                .toAggregate();
    }

    private Specification<GenreJpaEntity> assembleSpecification(final String terms) {
        return SpecificationUtils.like("name", terms);
    }
}

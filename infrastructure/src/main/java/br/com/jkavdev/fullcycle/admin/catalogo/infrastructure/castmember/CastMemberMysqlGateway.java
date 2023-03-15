package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMember;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberJpaEntity;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
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
public class CastMemberMysqlGateway implements CastMemberGateway {

    final private CastMemberRepository castMemberRepository;

    public CastMemberMysqlGateway(final CastMemberRepository castMemberRepository) {
        this.castMemberRepository = Objects.requireNonNull(castMemberRepository);
    }

    @Override
    public CastMember create(final CastMember aMember) {
        return save(aMember);
    }

    @Override
    public void deleteById(final CastMemberID aMemberId) {
        final var anId = aMemberId.getValue();
        if (castMemberRepository.existsById(anId)) {
            castMemberRepository.deleteById(anId);
        }
    }

    @Override
    public Optional<CastMember> findById(final CastMemberID anId) {
        return castMemberRepository.findById(anId.getValue())
                .map(CastMemberJpaEntity::toAggregate);
    }

    @Override
    public CastMember update(final CastMember aMember) {
        return save(aMember);
    }

    @Override
    public Pagination<CastMember> findAll(final SearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var where = Optional.ofNullable(aQuery.terms())
                .filter(s -> !s.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult =
                this.castMemberRepository.findAll(where, page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CastMemberJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public List<CastMemberID> existsByIds(Iterable<CastMemberID> membersIDs) {
        final var ids = StreamSupport.stream(membersIDs.spliterator(), false)
                .map(CastMemberID::getValue)
                .toList();
        return castMemberRepository.existsByIds(ids).stream()
                .map(CastMemberID::from)
                .toList();
    }

    private Specification<CastMemberJpaEntity> assembleSpecification(final String terms) {
        return SpecificationUtils.like("name", terms);
    }

    private CastMember save(final CastMember aMember) {
        return this.castMemberRepository.save(CastMemberJpaEntity.from(aMember))
                .toAggregate();
    }
}

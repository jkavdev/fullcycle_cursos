package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMember;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CastMemberMysqlGateway implements CastMemberGateway {

    final private CastMemberRepository castMemberRepository;

    public CastMemberMysqlGateway(final CastMemberRepository castMemberRepository) {
        this.castMemberRepository = Objects.requireNonNull(castMemberRepository);
    }

    @Override
    public CastMember create(final CastMember aMember) {
        return null;
    }

    @Override
    public void deleteById(final CastMemberID anId) {

    }

    @Override
    public Optional<CastMember> findById(final CastMemberID anId) {
        return Optional.empty();
    }

    @Override
    public CastMember update(final CastMember aMember) {
        return null;
    }

    @Override
    public Pagination<CastMember> findAll(final SearchQuery aQuery) {
        return null;
    }
}

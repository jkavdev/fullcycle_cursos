package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.persistence;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.genre.persistence.GenreJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CastMemberRepository extends JpaRepository<CastMemberJpaEntity, String> {

    Page<GenreJpaEntity> findAll(Specification<GenreJpaEntity> whereClause, Pageable page);

}

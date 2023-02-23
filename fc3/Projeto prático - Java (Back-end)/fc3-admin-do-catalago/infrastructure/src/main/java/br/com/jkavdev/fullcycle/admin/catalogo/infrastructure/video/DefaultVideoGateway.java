package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.Identifier;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.*;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.utils.SqlUtils;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence.VideoJpaEntity;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence.VideoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.CollectionUtils.mapTo;
import static br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.CollectionUtils.nullIfEmpty;

public class DefaultVideoGateway implements VideoGateway {

    private final VideoRepository videoRepository;

    public DefaultVideoGateway(final VideoRepository videoRepository) {
        this.videoRepository = Objects.requireNonNull(videoRepository);
    }

    @Override
    @Transactional
    public Video create(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    public void deleteById(final VideoID anId) {
        final var anIdValue = anId.getValue();
        if (videoRepository.existsById(anIdValue)) {
            videoRepository.deleteById(anIdValue);
        }
    }

    @Override
    @Transactional(readOnly = true)
    // ativando transacao caso algum dos relacionamento necessite de uma consulta extra, EAGER
    public Optional<Video> findById(final VideoID anId) {
        return videoRepository.findById(anId.getValue())
                .map(VideoJpaEntity::toAggregate);
    }

    @Override
    @Transactional
    public Video update(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    public Pagination<VideoPreview> findAll(final VideoSearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var actualResult = this.videoRepository.findAll(
                SqlUtils.like(aQuery.terms()),
                nullIfEmpty(mapTo(aQuery.castMembers(), Identifier::getValue)),
                nullIfEmpty(mapTo(aQuery.categories(), Identifier::getValue)),
                nullIfEmpty(mapTo(aQuery.genres(), Identifier::getValue)),
                page
        );


        return new Pagination<>(
                actualResult.getNumber(),
                actualResult.getSize(),
                actualResult.getTotalElements(),
                actualResult.toList()
        );
    }

    private Video save(final Video aVideo) {
        return this.videoRepository.save(VideoJpaEntity.from(aVideo))
                .toAggregate();
    }
}

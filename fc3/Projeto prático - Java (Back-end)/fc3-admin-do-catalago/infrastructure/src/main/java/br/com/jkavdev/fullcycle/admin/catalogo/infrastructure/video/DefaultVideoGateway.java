package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.pagination.Pagination;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Video;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoSearchQuery;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence.VideoJpaEntity;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence.VideoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

public class DefaultVideoGateway implements VideoGateway {

    private final VideoRepository videoRepository;

    public DefaultVideoGateway(final VideoRepository videoRepository) {
        this.videoRepository = Objects.requireNonNull(videoRepository);
    }

    @Override
    @Transactional
    public Video create(Video aVideo) {
        return this.videoRepository.save(VideoJpaEntity.from(aVideo))
                .toAggregate();
    }

    @Override
    public void deleteById(final VideoID anId) {
        final var anIdValue = anId.getValue();
        if (videoRepository.existsById(anIdValue)) {
            videoRepository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<Video> findById(VideoID anId) {
        return Optional.empty();
    }

    @Override
    public Video update(Video aVideo) {
        return null;
    }

    @Override
    public Pagination<Video> findAll(VideoSearchQuery aQuery) {
        return null;
    }
}

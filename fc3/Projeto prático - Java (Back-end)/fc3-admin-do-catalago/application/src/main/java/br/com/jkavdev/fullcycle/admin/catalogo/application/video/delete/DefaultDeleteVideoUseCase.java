package br.com.jkavdev.fullcycle.admin.catalogo.application.video.delete;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoID;

import java.util.Objects;

public class DefaultDeleteVideoUseCase extends DeleteVideoUseCase {

    private final VideoGateway videoGateway;

    public DefaultDeleteVideoUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public void execute(String anId) {
        this.videoGateway.deleteById(VideoID.from(anId));
    }
}

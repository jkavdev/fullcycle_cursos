package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.usecases;

import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.update.upload.DefaultUpdateMediaStatusUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.update.upload.UpdateMediaStatusUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class VideoUseCaseConfiguration {

    private final VideoGateway videoGateway;

    public VideoUseCaseConfiguration(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Bean
    public UpdateMediaStatusUseCase updateMediaStatusUseCase() {
        return new DefaultUpdateMediaStatusUseCase(videoGateway);
    }
}

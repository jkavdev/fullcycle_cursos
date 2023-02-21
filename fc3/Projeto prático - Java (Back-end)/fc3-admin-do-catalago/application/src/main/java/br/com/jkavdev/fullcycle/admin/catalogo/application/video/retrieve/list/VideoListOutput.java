package br.com.jkavdev.fullcycle.admin.catalogo.application.video.retrieve.list;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Video;

import java.time.Instant;

// TODO: retornar na listagem de videos, os nomes das categorias, generos e membros

public record VideoListOutput(
        String id,
        String title,
        String description,
        Instant createdAt,
        Instant deletedAt
) {

    public static VideoListOutput from(
            final Video aVideo
    ) {
        return new VideoListOutput(
                aVideo.getId().getValue(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt()
        );
    }
}

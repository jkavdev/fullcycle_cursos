package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.presenters;

import br.com.jkavdev.fullcycle.admin.catalogo.application.video.retrieve.get.VideoOutput;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.update.UpdateVideoOutput;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.AudioVideoMedia;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.ImageMedia;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.AudioVideoMediaResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.ImageMediaResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.UpdateVideoResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.VideoResponse;

public interface VideoApiPresenter {

    static VideoResponse present(final VideoOutput output) {
        return new VideoResponse(
                output.id(),
                output.title(),
                output.description(),
                output.duration(),
                output.launchedAt(),
                output.opened(),
                output.published(),
                output.rating().getName(),
                output.createdAt(),
                output.updatedAt(),
                present(output.banner()),
                present(output.thumbnail()),
                present(output.thumbnailHalf()),
                present(output.video()),
                present(output.trailer()),
                output.members(),
                output.categories(),
                output.genres()
        );
    }

    static ImageMediaResponse present(final ImageMedia image) {
        if (image == null) {
            return null;
        }

        return new ImageMediaResponse(
                image.id(),
                image.checksum(),
                image.name(),
                image.location()
        );
    }

    static AudioVideoMediaResponse present(final AudioVideoMedia video) {
        if (video == null) {
            return null;
        }

        return new AudioVideoMediaResponse(
                video.id(),
                video.checksum(),
                video.name(),
                video.rawLocation(),
                video.encodedLocation(),
                video.status().name()
        );
    }

    static UpdateVideoResponse present(final UpdateVideoOutput output) {
        return new UpdateVideoResponse(output.id());
    }
}

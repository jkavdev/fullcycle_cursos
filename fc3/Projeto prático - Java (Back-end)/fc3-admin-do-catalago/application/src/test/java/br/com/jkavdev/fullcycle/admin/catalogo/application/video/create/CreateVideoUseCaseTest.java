package br.com.jkavdev.fullcycle.admin.catalogo.application.video.create;

import br.com.jkavdev.fullcycle.admin.catalogo.application.Fixture;
import br.com.jkavdev.fullcycle.admin.catalogo.application.UseCaseTest;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Resource;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Resource.Type;

public class CreateVideoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateVideoUseCase useCase;

    @Mock
    private VideoGateway videoGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Mock
    private GenreGateway genreGateway;


    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway, categoryGateway, genreGateway, castMemberGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateVideo_shouldReturnVideoId() {
        // given
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(Fixture.Categories.aulas().getId());
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.of(
                Fixture.CastMembers.wesley().getId(),
                Fixture.CastMembers.gabriel().getId()
        );
        final Resource expectedVideo = Fixture.Videos.resource(Type.VIDEO);
        final Resource expectedTrailer = Fixture.Videos.resource(Type.TRAILER);
        final Resource expectedBanner = Fixture.Videos.resource(Type.BANNER);
        final Resource expectedThumb = Fixture.Videos.resource(Type.THUMBNAIL);
        final Resource expectedThumbHalf = Fixture.Videos.resource(Type.THUMBNAIL_HALF);

        final var aCommand = CreateVideoCommand.with(
                expectedTitle,
                expectedDescription,
                expectedLaunchYear.getValue(),
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating.getName(),
                asString(expectedCategories),
                asString(expectedGenres),
                asString(expectedMembers),
                expectedVideo,
                expectedTrailer,
                expectedBanner,
                expectedThumb,
                expectedThumbHalf
        );

        Mockito.when(categoryGateway.existsByIds(ArgumentMatchers.any()))
                .thenReturn(new ArrayList<>(expectedCategories));

        Mockito.when(castMemberGateway.existsByIds(ArgumentMatchers.any()))
                .thenReturn(new ArrayList<>(expectedMembers));

        Mockito.when(genreGateway.existsByIds(ArgumentMatchers.any()))
                .thenReturn(new ArrayList<>(expectedGenres));

        Mockito.when(videoGateway.create(ArgumentMatchers.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        // when
        final var actualResult = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualResult);
        Assertions.assertNotNull(actualResult.id());

        Mockito.verify(videoGateway).create(ArgumentMatchers.argThat(actualVideo ->
                        Objects.equals(expectedTitle, actualVideo.getTitle())
                                && Objects.equals(expectedDescription, actualVideo.getDescription())
                                && Objects.equals(expectedLaunchYear, actualVideo.getLaunchedAt())
                                && Objects.equals(expectedDuration, actualVideo.getDuration())
                                && Objects.equals(expectedOpened, actualVideo.getOpened())
                                && Objects.equals(expectedPublished, actualVideo.getPublished())
                                && Objects.equals(expectedRating, actualVideo.getRating())
                                && Objects.equals(expectedCategories, actualVideo.getCategories())
                                && Objects.equals(expectedGenres, actualVideo.getGenres())
                                && Objects.equals(expectedMembers, actualVideo.getCastMembers())
//                        && Objects.equals(expectedVideo.name(), actualVideo.getVideo().get().name())
//                        && Objects.equals(expectedTrailer.name(), actualVideo.getTrailer().get().name())
//                        && Objects.equals(expectedBanner.name(), actualVideo.getBanner().get().name())
//                        && Objects.equals(expectedThumb.name(), actualVideo.getThumbnail().get().name())
//                        && Objects.equals(expectedThumbHalf.name(), actualVideo.getThumbnailHalf().get().name())
        ));
    }
}

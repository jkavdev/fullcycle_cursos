package br.com.jkavdev.fullcycle.admin.catalogo.domain.video;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.AggregateRoot;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.InstantUtils;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.time.Year;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Video extends AggregateRoot<VideoID> {

    private String title;

    private String description;

    private Year launchedAt;

    private double duration;

    private Rating rating;

    private boolean opened;

    private boolean published;

    private Instant createdAt;

    private Instant updatedAt;

    private ImageMedia banner;

    private ImageMedia thumbnail;

    private ImageMedia thumbnailHalf;

    private AudioVideoMedia trailer;

    private AudioVideoMedia video;

    private Set<CategoryID> categories;
    private Set<GenreID> genres;
    private Set<CastMemberID> castMembers;


    protected Video(
            final VideoID anId,
            final String aTitle,
            final String aDescription,
            final Year aLaunchYear,
            final double aDuration,
            final boolean wasOpened,
            final boolean wasPublished,
            final Rating aRating,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final ImageMedia aBanner,
            final ImageMedia aThumb,
            final ImageMedia aThumbHalf,
            final AudioVideoMedia aTrailer,
            final AudioVideoMedia aVideo,
            final Set<CategoryID> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> castMembers
    ) {
        super(anId);
        this.title = aTitle;
        this.description = aDescription;
        this.launchedAt = aLaunchYear;
        this.duration = aDuration;
        this.opened = wasOpened;
        this.published = wasPublished;
        this.rating = aRating;
        this.createdAt = aCreationDate;
        this.updatedAt = aUpdateDate;
        this.banner = aBanner;
        this.thumbnail = aThumb;
        this.thumbnailHalf = aThumbHalf;
        this.trailer = aTrailer;
        this.video = aVideo;
        this.categories = categories;
        this.genres = genres;
        this.castMembers = castMembers;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new VideoValidator(this, handler).validate();
    }

    public static Video newVideo(
            final String aTitle,
            final String aDescription,
            final Year aLaunchYear,
            final double aDuration,
            final boolean wasOpened,
            final boolean wasPublished,
            final Rating aRating,
            final Set<CategoryID> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> members
    ) {
        final var now = InstantUtils.now();
        final var anId = VideoID.unique();
        return new Video(
                anId,
                aTitle,
                aDescription,
                aLaunchYear,
                aDuration,
                wasOpened,
                wasPublished,
                aRating,
                now,
                now,
                null,
                null,
                null,
                null,
                null,
                categories,
                genres,
                members
        );
    }

    public static Video with(
            final VideoID anId,
            final String aTitle,
            final String aDescription,
            final Year aLaunchYear,
            final double aDuration,
            final boolean wasOpened,
            final boolean wasPublished,
            final Rating aRating,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final ImageMedia aBanner,
            final ImageMedia aThumb,
            final ImageMedia aThumbHalf,
            final AudioVideoMedia aTrailer,
            final AudioVideoMedia aVideo,
            final Set<CategoryID> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> castMembers
    ) {
        return new Video(
                anId,
                aTitle,
                aDescription,
                aLaunchYear,
                aDuration,
                wasOpened,
                wasPublished,
                aRating,
                aCreationDate,
                aUpdateDate,
                aBanner,
                aThumb,
                aThumbHalf,
                aTrailer,
                aVideo,
                categories,
                genres,
                castMembers
        );
    }

    public static Video with(final Video aVideo) {
        return new Video(
                aVideo.getId(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getLaunchedAt(),
                aVideo.getDuration(),
                aVideo.getOpened(),
                aVideo.getPublished(),
                aVideo.getRating(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getBanner().orElse(null),
                aVideo.getThumbnail().orElse(null),
                aVideo.getThumbnailHalf().orElse(null),
                aVideo.getTrailer().orElse(null),
                aVideo.getVideo().orElse(null),
                new HashSet<>(aVideo.getCategories()),
                new HashSet<>(aVideo.getGenres()),
                new HashSet<>(aVideo.getCastMembers())
        );
    }

    public Video update(
            final String aTitle,
            final String aDescription,
            final Year aLaunchYear,
            final double aDuration,
            final boolean wasOpened,
            final boolean wasPublished,
            final Rating aRating,
            final Set<CategoryID> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> members
    ) {
        this.title = aTitle;
        this.description = aDescription;
        this.launchedAt = aLaunchYear;
        this.duration = aDuration;
        this.opened = wasOpened;
        this.published = wasPublished;
        this.rating = aRating;
        this.updatedAt = InstantUtils.now();
        this.setCategories(categories);
        this.setGenres(genres);
        this.setCastMembers(members);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Video setDescription(String description) {
        this.description = description;
        return this;
    }

    public Year getLaunchedAt() {
        return launchedAt;
    }

    public double getDuration() {
        return duration;
    }

    public Rating getRating() {
        return rating;
    }

    public boolean getOpened() {
        return opened;
    }

    public boolean getPublished() {
        return published;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Video setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Video setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Optional<ImageMedia> getBanner() {
        return Optional.ofNullable(this.banner);
    }

    public Video setBanner(ImageMedia banner) {
        this.banner = banner;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Optional<ImageMedia> getThumbnail() {
        return Optional.ofNullable(this.thumbnail);
    }

    public Video setThumbnail(ImageMedia thumbnail) {
        this.thumbnail = thumbnail;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Optional<ImageMedia> getThumbnailHalf() {
        return Optional.ofNullable(this.thumbnailHalf);
    }

    public Video setThumbnailHalf(ImageMedia thumbnailHalf) {
        this.thumbnailHalf = thumbnailHalf;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Optional<AudioVideoMedia> getTrailer() {
        return Optional.ofNullable(this.trailer);
    }

    public Video setTrailer(AudioVideoMedia trailer) {
        this.trailer = trailer;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Optional<AudioVideoMedia> getVideo() {
        return Optional.ofNullable(this.video);
    }

    public Video setVideo(AudioVideoMedia video) {
        this.video = video;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Set<CategoryID> getCategories() {
        return categories != null ? Collections.unmodifiableSet(categories) : Collections.emptySet();
    }

    public void setCategories(Set<CategoryID> categories) {
        this.categories = categories != null ? new HashSet<>(categories) : Collections.emptySet();
    }

    public Set<GenreID> getGenres() {
        return genres != null ? Collections.unmodifiableSet(genres) : Collections.emptySet();
    }

    public void setGenres(Set<GenreID> genres) {
        this.genres = genres != null ? new HashSet<>(genres) : Collections.emptySet();
    }

    public Set<CastMemberID> getCastMembers() {
        return castMembers != null ? Collections.unmodifiableSet(castMembers) : Collections.emptySet();
    }

    public void setCastMembers(Set<CastMemberID> castMembers) {
        this.castMembers = castMembers != null ? new HashSet<>(castMembers) : Collections.emptySet();
    }

    public Video processing(final VideoMediaType aType) {
        if (VideoMediaType.VIDEO == aType) {
            getVideo()
                    .ifPresent(media -> setVideo(media.processing()));
        } else if (VideoMediaType.TRAILER == aType) {
            getTrailer()
                    .ifPresent(media -> setTrailer(media.processing()));
        }

        return this;
    }

    public Video completed(final VideoMediaType aType, final String encodedPath) {
        if (VideoMediaType.VIDEO == aType) {
            getVideo()
                    .ifPresent(media -> setVideo(media.completed(encodedPath)));
        } else if (VideoMediaType.TRAILER == aType) {
            getTrailer()
                    .ifPresent(media -> setTrailer(media.completed(encodedPath)));
        }

        return this;
    }
}

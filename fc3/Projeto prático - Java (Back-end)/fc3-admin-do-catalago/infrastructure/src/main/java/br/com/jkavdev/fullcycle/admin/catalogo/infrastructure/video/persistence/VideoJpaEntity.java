package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.persistence;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.genre.GenreID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Rating;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.Video;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoID;

import javax.persistence.*;
import java.time.Instant;
import java.time.Year;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity(name = "Video")
@Table(name = "videos")
public class VideoJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "year_launched", nullable = false)
    private int yearLaunched;

    @Column(name = "opened", nullable = false)
    private boolean opened;

    @Column(name = "published", nullable = false)
    private boolean published;

    @Column(name = "rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(name = "duration", precision = 2)
    private double duration;

    @Column(name = "created_at", nullable = false, columnDefinition = "datetime(6)")
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false, columnDefinition = "datetime(6)")
    private Instant updatedAt;

    //    TODO: implementar relacionamentos com tabela PIVO
    @OneToOne(cascade = ALL, fetch = EAGER, orphanRemoval = true)
    @JoinColumn(name = "video_id")
    private AudioVideoMediaJpaEntity video;

    @OneToOne(cascade = ALL, fetch = EAGER, orphanRemoval = true)
    @JoinColumn(name = "trailer_id")
    private AudioVideoMediaJpaEntity trailer;

    @OneToOne(cascade = ALL, fetch = EAGER, orphanRemoval = true)
    @JoinColumn(name = "banner_id")
    private ImageMediaJpaEntity banner;

    @OneToOne(cascade = ALL, fetch = EAGER, orphanRemoval = true)
    @JoinColumn(name = "thumbnail_id")
    private ImageMediaJpaEntity thumbnail;

    @OneToOne(cascade = ALL, fetch = EAGER, orphanRemoval = true)
    @JoinColumn(name = "thumbnail_half_id")
    private ImageMediaJpaEntity thumbnailHalf;

    @OneToMany(mappedBy = "video", cascade = ALL, orphanRemoval = true)
    private Set<VideoCategoryJpaEntity> categories;

    @OneToMany(mappedBy = "video", cascade = ALL, orphanRemoval = true)
    private Set<VideoGenreJpaEntity> genres;

    @OneToMany(mappedBy = "video", cascade = ALL, orphanRemoval = true)
    private Set<VideoCastMemberJpaEntity> castMembers;

    private VideoJpaEntity() {
    }

    private VideoJpaEntity(
            final String id,
            final String title,
            final String description,
            final int yearLaunched,
            final boolean opened,
            final boolean published,
            final Rating rating,
            final double duration,
            final Instant createdAt,
            final Instant updatedAt,
            final AudioVideoMediaJpaEntity video,
            final AudioVideoMediaJpaEntity trailer,
            final ImageMediaJpaEntity banner,
            final ImageMediaJpaEntity thumbnail,
            final ImageMediaJpaEntity thumbnailHalf
    ) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;
        this.yearLaunched = yearLaunched;
        this.opened = opened;
        this.published = published;
        this.rating = rating;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.video = video;
        this.trailer = trailer;
        this.banner = banner;
        this.thumbnail = thumbnail;
        this.thumbnailHalf = thumbnailHalf;
        this.categories = new HashSet<>();
    }

    public static VideoJpaEntity from(final Video aVideo) {
        final var entity = new VideoJpaEntity(
                aVideo.getId().getValue(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getLaunchedAt().getValue(),
                aVideo.getOpened(),
                aVideo.getPublished(),
                aVideo.getRating(),
                aVideo.getDuration(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getVideo()
                        .map(AudioVideoMediaJpaEntity::from)
                        .orElse(null),
                aVideo.getTrailer()
                        .map(AudioVideoMediaJpaEntity::from)
                        .orElse(null),
                aVideo.getBanner()
                        .map(ImageMediaJpaEntity::from)
                        .orElse(null),
                aVideo.getThumbnail()
                        .map(ImageMediaJpaEntity::from)
                        .orElse(null),
                aVideo.getThumbnailHalf()
                        .map(ImageMediaJpaEntity::from)
                        .orElse(null));

        aVideo.getCategories()
                .forEach(entity::addCategory);

        aVideo.getGenres()
                .forEach(entity::addGenres);

        aVideo.getCastMembers()
                .forEach(entity::addMembers);

        return entity;
    }

    public Video toAggregate() {
        return Video.with(
                VideoID.from(getId()),
                getTitle(),
                getDescription(),
                Year.of(getYearLaunched()),
                getDuration(),
                isOpened(),
                isPublished(),
                getRating(),
                getCreatedAt(),
                getUpdatedAt(),
                Optional.ofNullable(getBanner())
                        .map(ImageMediaJpaEntity::toDomain)
                        .orElse(null),
                Optional.ofNullable(getThumbnail())
                        .map(ImageMediaJpaEntity::toDomain)
                        .orElse(null),
                Optional.ofNullable(getThumbnailHalf())
                        .map(ImageMediaJpaEntity::toDomain)
                        .orElse(null),
                Optional.ofNullable(getTrailer())
                        .map(AudioVideoMediaJpaEntity::toDomain)
                        .orElse(null),
                Optional.ofNullable(getVideo())
                        .map(AudioVideoMediaJpaEntity::toDomain)
                        .orElse(null),
                getCategories().stream()
                        .map(it -> CategoryID.from(it.getId().getCategoryId()))
                        .collect(Collectors.toSet()),
                getGenres().stream()
                        .map(it -> GenreID.from(it.getId().getGenreId()))
                        .collect(Collectors.toSet()),
                getCastMembers().stream()
                        .map(it -> CastMemberID.from(it.getId().getCastMemberId()))
                        .collect(Collectors.toSet())
        );
    }

    public void addCategory(final CategoryID categoryID) {
        this.categories.add(VideoCategoryJpaEntity.from(this, categoryID));
    }

    public void addGenres(final GenreID genreID) {
        this.genres.add(VideoGenreJpaEntity.from(this, genreID));
    }

    public void addMembers(final CastMemberID memberID) {
        this.castMembers.add(VideoCastMemberJpaEntity.from(this, memberID));
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getYearLaunched() {
        return yearLaunched;
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isPublished() {
        return published;
    }

    public Rating getRating() {
        return rating;
    }

    public double getDuration() {
        return duration;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public AudioVideoMediaJpaEntity getVideo() {
        return video;
    }

    public AudioVideoMediaJpaEntity getTrailer() {
        return trailer;
    }

    public ImageMediaJpaEntity getBanner() {
        return banner;
    }

    public ImageMediaJpaEntity getThumbnail() {
        return thumbnail;
    }

    public ImageMediaJpaEntity getThumbnailHalf() {
        return thumbnailHalf;
    }

    public Set<VideoCategoryJpaEntity> getCategories() {
        return categories;
    }

    public Set<VideoGenreJpaEntity> getGenres() {
        return genres;
    }

    public Set<VideoCastMemberJpaEntity> getCastMembers() {
        return castMembers;
    }
}

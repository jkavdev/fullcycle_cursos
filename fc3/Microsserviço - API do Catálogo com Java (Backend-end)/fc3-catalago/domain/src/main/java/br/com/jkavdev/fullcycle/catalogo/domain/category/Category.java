package br.com.jkavdev.fullcycle.catalogo.domain.category;

import br.com.jkavdev.fullcycle.catalogo.domain.validation.Error;
import br.com.jkavdev.fullcycle.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category {

    private final String id;

    private final String name;

    private final String description;

    private final boolean active;

    private final Instant createdAt;

    private final Instant updatedAt;

    private final Instant deletedAt;

    private Category(
            final String anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt
    ) {
        this.id = anId;
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = aCreatedAt;
        this.updatedAt = aUpdatedAt;
        this.deletedAt = aDeletedAt;
    }

    public static Category with(
            final String anId,
            final String aName,
            final String aDescription,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Category(
                anId,
                aName,
                aDescription,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Category with(final Category aCategory) {
        return with(
                aCategory.id(),
                aCategory.name(),
                aCategory.description(),
                aCategory.active(),
                aCategory.createdAt(),
                aCategory.updatedAt(),
                aCategory.deletedAt()
        );
    }

    public Category validate(final ValidationHandler aHandler) {
        if (id == null || id.isEmpty()) {
            aHandler.append(new Error("'id' should not be empty"));
        }

        if (name == null || name.isEmpty()) {
            aHandler.append(new Error("'name' should not be empty"));
        }
        return this;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public boolean active() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public Instant deletedAt() {
        return deletedAt;
    }
}

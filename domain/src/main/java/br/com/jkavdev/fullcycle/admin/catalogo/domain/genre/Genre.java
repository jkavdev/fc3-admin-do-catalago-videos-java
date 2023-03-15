package br.com.jkavdev.fullcycle.admin.catalogo.domain.genre;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.AggregateRoot;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.category.CategoryID;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.exceptions.NotificationException;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.InstantUtils;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.ValidationHandler;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Genre extends AggregateRoot<GenreID> implements Cloneable {

    private GenreID id;

    private String name;

    private boolean active;

    private List<CategoryID> categories;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    private Genre(
            final GenreID anId,
            final String aName,
            final boolean isActive,
            final List<CategoryID> categories,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt
    ) {
        super(anId);
        this.id = anId;
        this.name = aName;
        this.active = isActive;
        this.categories = new ArrayList<>(categories != null ? categories : Collections.emptyList());
        this.createdAt = Objects.requireNonNull(aCreatedAt, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdatedAt, "'updatedAt' should not be null");
        this.deletedAt = aDeletedAt;

//        realizando validacao imediata, ao invez da tardia utilizada em categoria
        selfValidate();
    }

    public static Genre newGenre(final String name, final boolean isActive) {
        final var id = GenreID.unique();
        final var now = InstantUtils.now();
        final var deletedAt = isActive ? null : now;

        return new Genre(id, name, isActive, new ArrayList<>(), now, now, deletedAt);
    }

    public static Genre with(
            final GenreID anId,
            final String aName,
            final boolean active,
            final List<CategoryID> categories,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Genre(
                anId,
                aName,
                active,
                categories,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Genre with(final Genre aGenre) {
        return with(
                aGenre.getId(),
                aGenre.name,
                aGenre.isActive(),
                aGenre.getCategories(),
                aGenre.createdAt,
                aGenre.updatedAt,
                aGenre.deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new GenreValidator(this, handler).validate();
    }

    public Genre deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = InstantUtils.now();
        }

        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public GenreID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Genre update(
            final String aName,
            final boolean isActive,
            final List<CategoryID> categories
    ) {
        if (isActive) {
            activate();
        } else {
            deactivate();
        }

        this.name = aName;
        this.active = isActive;
        this.categories = new ArrayList<>(categories != null ? categories : Collections.emptyList());
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("failed to create a aggregate genre", notification);
        }
    }

    @Override
    public Genre clone() {
        try {
            return (Genre) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Genre addCategory(final CategoryID aCategoryID) {
        if (aCategoryID == null) {
            return this;
        }

        categories.add(aCategoryID);
        updatedAt = InstantUtils.now();
        return this;
    }

    public Genre addCategories(final List<CategoryID> categories) {
        if (categories == null || categories.isEmpty()) {
            return this;
        }

        this.categories.addAll(categories);
        updatedAt = InstantUtils.now();
        return this;
    }

    public Genre removeCategory(final CategoryID aCategoryID) {
        if (aCategoryID == null) {
            return this;
        }

        categories.remove(aCategoryID);
        updatedAt = InstantUtils.now();
        return this;
    }
}

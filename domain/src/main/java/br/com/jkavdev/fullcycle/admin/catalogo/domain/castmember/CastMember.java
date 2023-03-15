package br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.AggregateRoot;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.exceptions.NotificationException;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.InstantUtils;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.ValidationHandler;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.handler.Notification;

import java.time.Instant;

public class CastMember extends AggregateRoot<CastMemberID> {

    private String name;

    private CastMemberType type;

    private Instant createdAt;

    private Instant updatedAt;

    protected CastMember(
            final CastMemberID anId,
            final String aName,
            final CastMemberType aType,
            final Instant aCreationDate,
            final Instant anUpdateDate
    ) {
        super(anId);
        this.name = aName;
        this.type = aType;
        this.createdAt = aCreationDate;
        this.updatedAt = anUpdateDate;
        selfValidate();
    }

    public static CastMember newMember(final String aName, final CastMemberType aType) {
        final var anId = CastMemberID.unique();
        final var now = InstantUtils.now();
        return new CastMember(anId, aName, aType, now, now);
    }

    public static CastMember with(
            final CastMemberID anId,
            final String aName,
            final CastMemberType aType,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new CastMember(
                anId,
                aName,
                aType,
                createdAt,
                updatedAt
        );
    }

    public static CastMember with(final CastMember aMember) {
        return with(
                aMember.getId(),
                aMember.name,
                aMember.type,
                aMember.createdAt,
                aMember.updatedAt
        );
    }

    public CastMember update(final String aName, final CastMemberType aType) {
        this.name = aName;
        this.type = aType;
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    @Override
    public void validate(final ValidationHandler aHandler) {
        new CastMemberValidator(this, aHandler).validate();
    }

    public String getName() {
        return name;
    }

    public CastMemberType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("failed to create a aggregate castmember", notification);
        }

    }
}

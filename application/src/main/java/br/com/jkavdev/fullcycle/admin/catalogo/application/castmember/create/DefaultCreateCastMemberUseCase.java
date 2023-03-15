package br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMember;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.exceptions.NotificationException;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.handler.Notification;

import java.util.Objects;

public non-sealed class DefaultCreateCastMemberUseCase extends CreateCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultCreateCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public CreateCastMemberOutput execute(final CreateCastMemberCommand aCommand) {
        final var name = aCommand.name();
        final var type = aCommand.type();

        final var notification = Notification.create();

        final var aMember = notification.validate(() -> CastMember.newMember(name, type));

        if (notification.hasError()) {
            notify(notification);
        }

        return CreateCastMemberOutput.from(castMemberGateway.create(aMember));
    }

    private void notify(final Notification notification) {
        throw new NotificationException("could not create aggregate cast member", notification);
    }
}

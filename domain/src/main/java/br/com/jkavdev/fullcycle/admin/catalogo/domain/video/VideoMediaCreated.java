package br.com.jkavdev.fullcycle.admin.catalogo.domain.video;


import br.com.jkavdev.fullcycle.admin.catalogo.domain.event.DomainEvent;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.InstantUtils;

import java.time.Instant;

public record VideoMediaCreated(
        String resourceId,
        String filePath,
        Instant occurredOn
) implements DomainEvent {

    public VideoMediaCreated(final String resourceId, final String filePath) {
        this(resourceId, filePath, InstantUtils.now());
    }
}

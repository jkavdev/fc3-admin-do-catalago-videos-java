package br.com.jkavdev.fullcycle.admin.catalogo.domain.event;

@FunctionalInterface
public interface DomainEventPublisher {

    void publishEvent(DomainEvent event);
}

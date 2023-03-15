package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.amqp.QueueProperties;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.annotations.VideoCreatedQueue;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.EventService;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.impl.RabbitEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    @VideoCreatedQueue
    public EventService videoCreatedEventService(
            @VideoCreatedQueue final QueueProperties props,
            final RabbitOperations ops
    ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}

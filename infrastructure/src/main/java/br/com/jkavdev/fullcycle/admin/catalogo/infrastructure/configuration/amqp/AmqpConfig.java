package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.amqp;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.annotations.VideoCreatedQueue;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.annotations.VideoEncodedQueue;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.annotations.VideoEvents;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    @ConfigurationProperties("amqp.queues.video-created")
    @VideoCreatedQueue
    public QueueProperties videoCreatedQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    @ConfigurationProperties("amqp.queues.video-encoded")
    @VideoEncodedQueue
    public QueueProperties videoEncodedQueueProperties() {
        return new QueueProperties();
    }

    /**
     * configurando a estrutura das filas, nao eh necessario, pois apenas precisamos dos listeners e producers
     *
     * mas para a niver de testes e desenvolvimentos, com essa configuracao, o spring eh capaz de criar a estrutura necessaria
     * da exchange, queues e bindings
     * atuando como infrastructure as a code
     */
    @Configuration
    static class Admin {

        @Bean
        @VideoEvents
        public Exchange videoEventsExchange(@VideoCreatedQueue QueueProperties props) {
            return new DirectExchange(props.getExchange());
        }

        @Bean
        @VideoCreatedQueue
        public Queue videoCreatedQueue(@VideoCreatedQueue QueueProperties props) {
            return new Queue(props.getQueue());
        }

        @Bean
        @VideoCreatedQueue
        public Binding videoCreatedBinding(
                @VideoEvents DirectExchange exchange,
                @VideoCreatedQueue Queue queue,
                @VideoCreatedQueue QueueProperties props
        ) {
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }

        @Bean
        @VideoEncodedQueue
        public Queue videoEncodedQueue(@VideoEncodedQueue QueueProperties props) {
            return new Queue(props.getQueue());
        }

        @Bean
        @VideoEncodedQueue
        public Binding videoEncodedBinding(
                @VideoEvents DirectExchange exchange,
                @VideoEncodedQueue Queue queue,
                @VideoEncodedQueue QueueProperties props
        ) {
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }
    }
}

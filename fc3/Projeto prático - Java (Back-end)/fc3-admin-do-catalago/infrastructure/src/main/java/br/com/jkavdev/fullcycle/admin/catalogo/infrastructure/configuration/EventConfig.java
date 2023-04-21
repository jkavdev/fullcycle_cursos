package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.amqp.QueueProperties;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.annotations.VideoCreatedQueue;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.EventService;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.impl.RabbitEventService;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.local.InMemoryEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EventConfig {

    @Bean
    @VideoCreatedQueue
    @Profile({"development"})
    public EventService localVideoCreatedEventService() {
        return new InMemoryEventService();
    }

    @Bean
    @VideoCreatedQueue
    @ConditionalOnMissingBean
    public EventService videoCreatedEventService(
            @VideoCreatedQueue final QueueProperties props,
            final RabbitOperations ops
    ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}

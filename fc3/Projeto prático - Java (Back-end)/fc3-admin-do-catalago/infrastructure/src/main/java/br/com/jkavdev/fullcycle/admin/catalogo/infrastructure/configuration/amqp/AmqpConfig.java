package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.amqp;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.annotations.VideoCreatedQueue;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.annotations.VideoEncodedQueue;
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

    @Bean
    public String queueName(@VideoCreatedQueue QueueProperties props) {
        return props.getQueue();
    }
}

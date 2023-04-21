package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.local;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.Json;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryEventService implements EventService {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryEventService.class);

    @Override
    public void send(Object event) {
        LOG.info("Event was observed: {}", Json.writeValueAsString(event));
    }
}

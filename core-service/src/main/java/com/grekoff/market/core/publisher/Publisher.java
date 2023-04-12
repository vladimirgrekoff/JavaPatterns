package com.grekoff.market.core.publisher;

import com.grekoff.market.core.event.ChangedDBProductsEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    private final ApplicationEventPublisher eventPublisher;

    Publisher(ApplicationEventPublisher publisher) {
        this.eventPublisher = publisher;
    }

    public void publishChangedDBProductsEvent(ChangedDBProductsEvent event) {
        eventPublisher.publishEvent(event);
    }

    public void publishMsgEvent(String msg) {
        eventPublisher.publishEvent(msg);
    }

}

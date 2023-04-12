package com.grekoff.market.core.listener;

import com.grekoff.market.core.event.ChangedDBProductsEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//@Component
public interface Listener {
    @Async
    @EventListener
    default void onEventReceived(ChangedDBProductsEvent event) {

    }

}

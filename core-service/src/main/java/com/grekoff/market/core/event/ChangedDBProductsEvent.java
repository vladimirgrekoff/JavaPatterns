package com.grekoff.market.core.event;

public class ChangedDBProductsEvent {
    private String message;

    public ChangedDBProductsEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

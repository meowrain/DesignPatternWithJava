package org.designpattern.listeners;

import org.designpattern.common.Event;
import org.designpattern.common.EventListener;

import java.util.function.Consumer;

public class Government implements EventListener {

    private final  String name;
    private final Consumer<String> consumer;
    public Government(String name, Consumer<String> consumer) {
        this.name = name;
        this.consumer = consumer;
    }
    private void receiveInfo(String info) {
        consumer.accept(info);
    }
    @Override
    public void onEvent(Event event) {
        receiveInfo(event.source().toString());
    }
}

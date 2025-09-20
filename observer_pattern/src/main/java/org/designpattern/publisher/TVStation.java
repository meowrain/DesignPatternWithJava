package org.designpattern.publisher;


import org.designpattern.common.Event;
import org.designpattern.common.EventListener;

import java.util.*;

public class TVStation {
    private final Map<Class<? extends Event>,List<EventListener>> listenerMap = new HashMap<>();
    private String name;
    public TVStation(String name) {
        this.name = name;
    }
    public void subscribe(EventListener listener, Class<? extends Event> eventClass) {
        listenerMap.computeIfAbsent(eventClass,k -> new ArrayList<>()).add(listener);
    }

    public void publish(Event event) {
        Class<? extends Event> aClass = event.getClass();
        List<EventListener> eventListeners = listenerMap.get(aClass);
        if (eventListeners!=null) {
            eventListeners.forEach(listener -> {
                listener.onEvent(event);
            });
        }
    }
}

package org.designpattern.events;

import org.designpattern.common.BaseEvent;

public class NewUpdateEvent extends BaseEvent {
    private final String info;
    public NewUpdateEvent(String info) {
        this.info = info;
    }

    @Override
    public Object source() {
        return info;
    }
}

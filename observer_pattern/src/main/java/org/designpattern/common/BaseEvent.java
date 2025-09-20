package org.designpattern.common;

public abstract class BaseEvent implements Event {
    private final  long timestamp;
    protected BaseEvent() {
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public long timestamp() {
        return timestamp;
    }
}

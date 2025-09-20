package org.designpattern.common;

public interface Event {
    long timestamp();
    Object source();
}

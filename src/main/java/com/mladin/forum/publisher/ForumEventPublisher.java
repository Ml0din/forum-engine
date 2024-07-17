package com.mladin.forum.publisher;

public interface ForumEventPublisher {
    void publishEvent(Object... data);
}

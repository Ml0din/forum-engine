package com.mladin.forum.events;

import org.springframework.context.ApplicationEvent;

public class ForumAccountCreationTwoFactorEvent extends ApplicationEvent {
    private Object persistentData;

    public ForumAccountCreationTwoFactorEvent(Object source, Object persistentData) {
        super(source);
        this.persistentData = persistentData;
    }

    public Object getPersistentData() {
        return this.persistentData;
    }
}

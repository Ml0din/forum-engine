package com.mladin.forum.publisher;

import com.mladin.forum.events.ForumAccountCreationTwoFactorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ForumAccountCreationTwoFactorEventPublisher implements ForumEventPublisher{
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishEvent(Object... data) {
        logger.info("Publishing two factor account creation challenge event.");

        ForumAccountCreationTwoFactorEvent accountCreationTwoFactorEvent = new ForumAccountCreationTwoFactorEvent(this, data[0]);
        applicationEventPublisher.publishEvent(accountCreationTwoFactorEvent);
    }
}

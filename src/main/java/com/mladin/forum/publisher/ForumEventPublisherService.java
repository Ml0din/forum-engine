package com.mladin.forum.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.logging.Logger;

@Component
public class ForumEventPublisherService {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private HashMap<String, ForumEventPublisher> publishers = new HashMap<>();

    @Autowired
    private ForumAccountCreationTwoFactorEventPublisher accountCreationTwoFactorEventPublisher;

    public void loadPublishers() {
        publishers.put("ACCOUNT_CREATION", accountCreationTwoFactorEventPublisher);
        publishers.put("SIGN_IN_CONFIRMATION", null);

        logger.info("Loaded " + publishers.size() + " event publishers.");
    }

    public ForumEventPublisher getPublisher(String name) {
        return publishers.get(name);
    }
}

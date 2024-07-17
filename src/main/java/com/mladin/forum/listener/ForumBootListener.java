package com.mladin.forum.listener;

import com.mladin.forum.email.ForumEmailManager;
import com.mladin.forum.publisher.ForumEventPublisherService;
import com.mladin.forum.service.ForumGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ForumBootListener implements ApplicationListener<ApplicationReadyEvent> {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private ForumGroupService forumGroupService;

    @Autowired
    private ForumEventPublisherService forumEventPublisherService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        forumGroupService.loadAllGroups();
        forumEventPublisherService.loadPublishers();

        logger.info("Forum started.");
    }
}

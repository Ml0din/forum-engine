package com.mladin.forum.listener;

import com.mladin.forum.data.ForumSignUpData;
import com.mladin.forum.events.ForumAccountCreationTwoFactorEvent;
import com.mladin.forum.security.ForumUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ForumAccountCreationTwoFactorListener implements ApplicationListener<ForumAccountCreationTwoFactorEvent> {
    private Logger logger = Logger.getLogger(ForumAccountCreationTwoFactorListener.class.getName());

    @Autowired
    private ForumUserManager forumUserManager;

    @Override
    public void onApplicationEvent(ForumAccountCreationTwoFactorEvent event) {
        ForumSignUpData forumSignUpData = (ForumSignUpData) event.getPersistentData();
        forumUserManager.createUser(forumSignUpData.getUsername(), forumSignUpData.getEmail(), forumSignUpData.getPassword());

        logger.info("Handled two factor account creation challenge event successfully.");
    }
}

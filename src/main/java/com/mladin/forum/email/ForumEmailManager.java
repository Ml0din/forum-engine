package com.mladin.forum.email;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Component
public class ForumEmailManager {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private ExecutorService emailService = Executors.newFixedThreadPool(20);

    @Autowired
    private Mailer mailer;

    public void sendSimpleEmailMessage(String to, String subject, String text) {
        emailService.execute(new Runnable() {
            @Override
            public void run() {
                Email email = EmailBuilder.startingBlank().from("no-reply@seventailed.com").to(to).withSubject(subject).withPlainText(text).buildEmail();
                mailer.sendMail(email);

                logger.info("Sent email to: " + to + " with subject: " + subject + ".");
            }
        });
    }
}

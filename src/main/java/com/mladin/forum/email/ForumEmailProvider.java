package com.mladin.forum.email;

import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ForumEmailProvider {
    @Bean
    public Mailer mailer() {
        return MailerBuilder.withSMTPServer("smtp.dreamhost.com", 587).withSMTPServerUsername("no-reply@seventailed.com").withSMTPServerPassword("TFjzD*4HrD?k_e6").buildMailer();
    }
}

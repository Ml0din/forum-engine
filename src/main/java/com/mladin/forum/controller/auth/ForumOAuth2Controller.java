package com.mladin.forum.controller.auth;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForumOAuth2Controller {
    @GetMapping("/oauth2")
    public String socials(Model model) {
        return "sign_in";
    }
}

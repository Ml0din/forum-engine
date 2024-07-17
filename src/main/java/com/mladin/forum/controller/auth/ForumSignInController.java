package com.mladin.forum.controller.auth;

import com.mladin.forum.utils.ForumAuthUtils;
import com.mladin.forum.utils.ForumErrorUtils;
import com.mladin.forum.utils.notification.ForumNotificationType;
import com.mladin.forum.utils.notification.ForumNotificationUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ForumSignInController {
    @GetMapping("/sign_in")
    public String login(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if(ForumAuthUtils.isAuthenticated(httpServletRequest)) {
            ForumNotificationUtils.notify(redirectAttributes, "You are already signed in.", ForumNotificationType.INFO);
            return "redirect:/";
        }

        return "sign_in";
    }
}

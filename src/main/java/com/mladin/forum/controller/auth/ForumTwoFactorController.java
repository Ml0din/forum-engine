package com.mladin.forum.controller.auth;

import com.mladin.forum.data.ForumTwoFactorData;
import com.mladin.forum.email.twofactor.ForumEmailTwoFactorManager;
import com.mladin.forum.utils.ForumErrorUtils;
import com.mladin.forum.utils.ForumPatternVerifier;
import com.mladin.forum.utils.notification.ForumNotificationType;
import com.mladin.forum.utils.notification.ForumNotificationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ForumTwoFactorController {
    @Autowired
    private ForumEmailTwoFactorManager forumEmailTwoFactorManager;

    @GetMapping("/two_factor")
    public String twoFactor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model, RedirectAttributes redirectAttributes) {
        if(!forumEmailTwoFactorManager.hasSession(httpServletRequest)) {
            ForumErrorUtils.error("Session non-existent.", redirectAttributes);
            return "redirect:/error";
        }

        if(forumEmailTwoFactorManager.sessionExpired(httpServletRequest, httpServletResponse)) {
            ForumErrorUtils.error("Session expired.", redirectAttributes);
            return "redirect:/error";
        }

        model.addAttribute("two_factor_data", new ForumTwoFactorData());
        return "two_factor";
    }

    @PostMapping("/two_factor")
    public RedirectView twoFactor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @ModelAttribute ForumTwoFactorData forumTwoFactorData, RedirectAttributes redirectAttributes) {
        if(!forumEmailTwoFactorManager.hasSession(httpServletRequest)) {
            return ForumErrorUtils.error("Session non-existent.", redirectAttributes);
        }

        if(forumEmailTwoFactorManager.sessionExpired(httpServletRequest, httpServletResponse)) {
            return ForumErrorUtils.error("Session expired.", redirectAttributes);
        }

        if(ForumPatternVerifier.verifyNumber(forumTwoFactorData.getCode())) {
            if (forumEmailTwoFactorManager.confirmSession(httpServletRequest, httpServletResponse, Integer.parseInt(forumTwoFactorData.getCode()))) {
                ForumNotificationUtils.notify(redirectAttributes, "Account created.", ForumNotificationType.INFO);
                return new RedirectView("/sign_in");
            } else {
                ForumNotificationUtils.notify(redirectAttributes, "Invalid code.", ForumNotificationType.ERROR);
                return new RedirectView("/two_factor");
            }
        }else {
            ForumNotificationUtils.notify(redirectAttributes, "Invalid code.", ForumNotificationType.ERROR);
            return new RedirectView("/two_factor");
        }
    }
}

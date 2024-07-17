package com.mladin.forum.controller.auth;

import com.mladin.forum.data.ForumSignUpData;
import com.mladin.forum.email.twofactor.ForumEmailTwoFactorManager;
import com.mladin.forum.email.twofactor.ForumEmailTwoFactorType;
import com.mladin.forum.service.ForumUserService;
import com.mladin.forum.utils.ForumAuthUtils;
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
public class ForumSignUpController {
    @Autowired
    private ForumUserService forumUserService;

    @Autowired
    private ForumEmailTwoFactorManager forumEmailTwoFactorManager;

    @GetMapping("/sign_up")
    public String register(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model, RedirectAttributes redirectAttributes) {
        if(ForumAuthUtils.isAuthenticated(httpServletRequest)) {
            ForumNotificationUtils.notify(redirectAttributes, "You are already signed in.", ForumNotificationType.INFO);
            return "redirect:/";
        }

        if(forumEmailTwoFactorManager.hasSession(httpServletRequest)) {
            if(!forumEmailTwoFactorManager.sessionExpired(httpServletRequest, httpServletResponse)) {
                ForumNotificationUtils.notify(redirectAttributes, "You need to complete the verification.", ForumNotificationType.ERROR);
                return "redirect:/two_factor";
            }
        }

        ForumSignUpData signUpData = new ForumSignUpData();
        model.addAttribute("signUpData", signUpData);

        return "sign_up";
    }

    @PostMapping("/sign_up")
    public RedirectView register(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @ModelAttribute ForumSignUpData forumSignUpData, RedirectAttributes redirectAttributes) {
        if(forumEmailTwoFactorManager.hasSession(httpServletRequest)) {
            if(!forumEmailTwoFactorManager.sessionExpired(httpServletRequest, httpServletResponse)) {
                ForumNotificationUtils.notify(redirectAttributes, "You need to complete the verification.", ForumNotificationType.ERROR);
                return new RedirectView("/two_factor");
            }
        }

        if(ForumPatternVerifier.verifyName(forumSignUpData.getUsername())) {
            if(!forumUserService.existsByUsername(forumSignUpData.getUsername())) {
                if(ForumPatternVerifier.verifyEmail(forumSignUpData.getEmail())) {
                    if(!forumUserService.existsUserByEmail(forumSignUpData.getEmail())) {
                        if(ForumPatternVerifier.verifyPassword(forumSignUpData.getPassword())) {
                            forumEmailTwoFactorManager.initializeSession(forumSignUpData.getEmail(), httpServletResponse, ForumEmailTwoFactorType.ACCOUNT_CREATION, forumSignUpData);
                            ForumNotificationUtils.notify(redirectAttributes, "Code was sent to your email.", ForumNotificationType.INFO);
                            return new RedirectView("/two_factor");
                        }else {
                            ForumNotificationUtils.notify(redirectAttributes, "Passwords can't contain invalid characters.", ForumNotificationType.ERROR);
                        }
                    }else {
                        ForumNotificationUtils.notify(redirectAttributes, "Email already used.", ForumNotificationType.ERROR);
                    }
                }else {
                    ForumNotificationUtils.notify(redirectAttributes, "Invalid email address.", ForumNotificationType.ERROR);
                }
            }else {
                ForumNotificationUtils.notify(redirectAttributes, "Account already exists.", ForumNotificationType.ERROR);
            }
        }else {
            ForumNotificationUtils.notify(redirectAttributes, "Invalid username.", ForumNotificationType.ERROR);
        }

        return new RedirectView("/sign_up");
    }
}

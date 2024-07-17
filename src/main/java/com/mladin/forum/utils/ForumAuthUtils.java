package com.mladin.forum.utils;

import com.mladin.forum.security.ForumAuthenticatedUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public class ForumAuthUtils {
    public static boolean isAuthenticated(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getUserPrincipal() != null && !(httpServletRequest.getUserPrincipal() instanceof AnonymousAuthenticationToken);
    }

    public static void includeAuthInformation(HttpServletRequest httpServletRequest, Model model) {
        Authentication authentication = (Authentication) httpServletRequest.getUserPrincipal();
        if(authentication.getPrincipal() instanceof ForumAuthenticatedUser) {
            ForumAuthenticatedUser authenticatedUser = (ForumAuthenticatedUser) authentication.getPrincipal();
            model.addAttribute("user", authenticatedUser);
        }
    }

    public static ForumAuthenticatedUser getForumUser(HttpServletRequest httpServletRequest) {
        Authentication authentication = (Authentication) httpServletRequest.getUserPrincipal();
        if(authentication.getPrincipal() instanceof ForumAuthenticatedUser) {
            ForumAuthenticatedUser authenticatedUser = (ForumAuthenticatedUser) authentication.getPrincipal();
            return authenticatedUser;
        }else {
            return null;
        }
    }
}

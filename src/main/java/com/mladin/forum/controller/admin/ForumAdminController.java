package com.mladin.forum.controller.admin;

import com.mladin.forum.utils.ForumAuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForumAdminController {
    @GetMapping("/admin")
    public String admin(HttpServletRequest httpServletRequest, Model model) {
        ForumAuthUtils.includeAuthInformation(httpServletRequest, model);
        return "admin";
    }
}
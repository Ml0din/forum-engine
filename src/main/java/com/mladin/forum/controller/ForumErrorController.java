package com.mladin.forum.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForumErrorController implements ErrorController {
    @GetMapping("/error")
    public String error(HttpServletRequest httpServletRequest, Model model) {
        Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null) {
            int errorCode = Integer.parseInt(status.toString());
            if(errorCode == 404) {
                model.addAttribute("errorStatus", true);
                model.addAttribute("errorMessage", "Can't find that page.");
            }else {
                model.addAttribute("errorStatus", true);
                model.addAttribute("errorMessage", "Internal server error.");
            }
        }

        return "error";
    }
}

package com.mladin.forum.utils;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

public class ForumErrorUtils {
    public static void invokeError(String errorMessage, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorStatus", true);
        redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
    }

    public static RedirectView error(String errorMessage, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorStatus", true);
        redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

        return new RedirectView("/error");
    }
}

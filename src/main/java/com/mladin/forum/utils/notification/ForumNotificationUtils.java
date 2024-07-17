package com.mladin.forum.utils.notification;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ForumNotificationUtils {
    public static void notify(Model model, String notificationMessage, ForumNotificationType notificationType) {
        model.addAttribute("notification", true);
        model.addAttribute("notificationType", notificationType.name());
        model.addAttribute("notificationMessage", notificationMessage);
    }

    public static void notify(RedirectAttributes redirectAttributes, String notificationMessage, ForumNotificationType notificationType) {
        redirectAttributes.addFlashAttribute("notification", true);
        redirectAttributes.addFlashAttribute("notificationType", notificationType.name());
        redirectAttributes.addFlashAttribute("notificationMessage", notificationMessage);
    }
}

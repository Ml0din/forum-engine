package com.mladin.forum.controller.topic;

import com.mladin.forum.ForumApplication;
import com.mladin.forum.data.ForumCreateTopicData;
import com.mladin.forum.entity.ForumTopicEntity;
import com.mladin.forum.repository.ForumSubcategoryRepository;
import com.mladin.forum.repository.ForumTopicRepository;
import com.mladin.forum.utils.ForumAuthUtils;
import com.mladin.forum.utils.ForumErrorUtils;
import com.mladin.forum.utils.ForumUUIDPool;
import com.mladin.forum.utils.notification.ForumNotificationType;
import com.mladin.forum.utils.notification.ForumNotificationUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ForumCreateTopicController {
    @Autowired
    private ForumSubcategoryRepository forumSubcategoryRepository;

    @Autowired
    private ForumTopicRepository forumTopicRepository;

    @GetMapping("/create_topic/{id}")
    public String createTopic(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        if(forumSubcategoryRepository.existsById(id)) {
            model.addAttribute("subcategory", id);
            model.addAttribute("topic", new ForumCreateTopicData());
            return "create_topic";
        }else {
            ForumErrorUtils.invokeError("Invalid page.", redirectAttributes);
            return "redirect:/error";
        }
    }

    @PostMapping("/create_topic/{id}")
    public RedirectView createTopic(HttpServletRequest httpServletRequest, @PathVariable String id, @ModelAttribute ForumCreateTopicData forumCreateTopicData, RedirectAttributes redirectAttributes) {
        if(forumSubcategoryRepository.existsById(id)) {
            ForumTopicEntity forumTopicEntity = new ForumTopicEntity(ForumUUIDPool.grabNewUUID(forumTopicRepository).toString(), forumCreateTopicData.getTitle(), forumCreateTopicData.getContent(), ForumAuthUtils.getForumUser(httpServletRequest).id(), ForumApplication.getDate(), id);
            forumTopicRepository.save(forumTopicEntity);

            ForumNotificationUtils.notify(redirectAttributes, "Topic created.", ForumNotificationType.INFO);
            return new RedirectView("/subcategory/" + id);
        }else {
            return ForumErrorUtils.error("Invalid page.", redirectAttributes);
        }
    }
}

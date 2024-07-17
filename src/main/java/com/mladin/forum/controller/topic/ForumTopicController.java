package com.mladin.forum.controller.topic;

import com.mladin.forum.entity.ForumTopicEntity;
import com.mladin.forum.repository.ForumTopicRepository;
import com.mladin.forum.repository.ForumUserRepository;
import com.mladin.forum.utils.ForumErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ForumTopicController {
    @Autowired
    private ForumUserRepository forumUserRepository;

    @Autowired
    private ForumTopicRepository forumTopicRepository;

    @GetMapping("/topic/{id}")
    public String viewTopic(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        if(forumTopicRepository.existsById(id)) {
            ForumTopicEntity forumTopicEntity = forumTopicRepository.findById(id).get();
            forumTopicEntity.setAuthorName(forumUserRepository.findById(forumTopicEntity.getAuthor()).get().getUsername());

            model.addAttribute("topic", forumTopicEntity);

            return "topic";
        }else {
            ForumErrorUtils.invokeError("Invalid page.", redirectAttributes);
            return "redirect:/error";
        }
    }
}

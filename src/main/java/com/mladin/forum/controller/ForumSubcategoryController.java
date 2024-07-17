package com.mladin.forum.controller;

import com.mladin.forum.entity.ForumSubcategoryEntity;
import com.mladin.forum.entity.ForumTopicEntity;
import com.mladin.forum.repository.ForumSubcategoryRepository;
import com.mladin.forum.repository.ForumTopicRepository;
import com.mladin.forum.repository.ForumUserRepository;
import com.mladin.forum.utils.ForumErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ForumSubcategoryController {
    @Autowired
    private ForumUserRepository forumUserRepository;

    @Autowired
    private ForumSubcategoryRepository forumSubcategoryRepository;

    @Autowired
    private ForumTopicRepository forumTopicRepository;

    @GetMapping("/subcategory/{id}")
    public String subcategory(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        if(forumSubcategoryRepository.existsById(id)) {
            ForumSubcategoryEntity subcategory = forumSubcategoryRepository.findById(id).get();
            model.addAttribute("subcategory", subcategory);

            List<ForumTopicEntity> topicEntities = forumTopicRepository.findAllBySubcategory(id);
            topicEntities.forEach(topic -> {
                topic.setAuthorName(forumUserRepository.findById(topic.getAuthor()).get().getUsername());
            });

            model.addAttribute("topics", topicEntities);

            return "subcategory";
        }else {
            ForumErrorUtils.invokeError("Invalid page.", redirectAttributes);
            return "redirect:/error";
        }
    }

}

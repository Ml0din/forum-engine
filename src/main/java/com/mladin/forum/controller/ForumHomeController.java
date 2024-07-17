package com.mladin.forum.controller;

import com.mladin.forum.entity.ForumCategoryEntity;
import com.mladin.forum.entity.ForumSubcategoryEntity;
import com.mladin.forum.repository.ForumCategoryRepository;
import com.mladin.forum.repository.ForumSubcategoryRepository;
import com.mladin.forum.utils.ForumAuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class ForumHomeController {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private ForumCategoryRepository forumCategoryRepository;

    @Autowired
    private ForumSubcategoryRepository forumSubcategoryRepository;

    @GetMapping("/")
    public String home(HttpServletRequest httpServletRequest, Model model) {
        List<ForumCategoryEntity> categoryEntities = forumCategoryRepository.findAll();
        categoryEntities.forEach(category -> {
            List<ForumSubcategoryEntity> subcategoryEntities = forumSubcategoryRepository.findByCategory(category.getId());
            category.setSubcategoryEntities(subcategoryEntities);
        });

        model.addAttribute("categories", forumCategoryRepository.findAll());

        if(ForumAuthUtils.isAuthenticated(httpServletRequest)) {
            ForumAuthUtils.includeAuthInformation(httpServletRequest, model);
        }

        return "home";
    }
}

package com.mladin.forum.controller.group;

import com.mladin.forum.data.ForumCreateGroupData;
import com.mladin.forum.service.ForumGroupService;
import com.mladin.forum.utils.ForumAuthUtils;
import com.mladin.forum.utils.ForumPatternVerifier;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ForumCreateGroupController {
    @Autowired
    private ForumGroupService forumGroupService;

    @GetMapping("/create_group")
    public String createGroup(HttpServletRequest httpServletRequest, Model model) {
        ForumAuthUtils.includeAuthInformation(httpServletRequest, model);
        model.addAttribute("create_group_data", new ForumCreateGroupData());

        return "/group/create_group";
    }

    @PostMapping("/create_group")
    public RedirectView createGroup(@ModelAttribute ForumCreateGroupData forumCreateGroupData, RedirectAttributes redirectAttributes) {
        if(ForumPatternVerifier.verifyName(forumCreateGroupData.getName())) {
            if(!forumGroupService.groupExists(forumCreateGroupData.getName())) {
                if(ForumPatternVerifier.verifyNumber(forumCreateGroupData.getPriority())) {
                    boolean created = forumGroupService.createEmptyGroup(forumCreateGroupData.getName(), forumCreateGroupData.getDisplay(), forumCreateGroupData.getPriority());

                    if(created) {
                        redirectAttributes.addFlashAttribute("notification", true);
                        redirectAttributes.addFlashAttribute("notificationMessage", "Created group successfully.");

                        return new RedirectView("edit_authorities");
                    }else {
                        redirectAttributes.addFlashAttribute("notification", true);
                        redirectAttributes.addFlashAttribute("notificationMessage", "Error occurred.");

                        return new RedirectView("create_group");
                    }
                }else {
                    redirectAttributes.addFlashAttribute("notification", true);
                    redirectAttributes.addFlashAttribute("notificationMessage", "Invalid priority.");

                    return new RedirectView("create_group");
                }
            }else {
                redirectAttributes.addFlashAttribute("notification", true);
                redirectAttributes.addFlashAttribute("notificationMessage", "Group already exists.");

                return new RedirectView("create_group");
            }
        }else {
            redirectAttributes.addFlashAttribute("notification", true);
            redirectAttributes.addFlashAttribute("notificationMessage", "Invalid group name.");

            return new RedirectView("create_group");
        }
    }
}

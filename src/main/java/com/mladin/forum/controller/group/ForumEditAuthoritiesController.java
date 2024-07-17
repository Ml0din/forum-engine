package com.mladin.forum.controller.group;

import com.mladin.forum.repository.ForumGroupRepository;
import com.mladin.forum.service.ForumGroupService;
import com.mladin.forum.utils.ForumAuthUtils;
import com.mladin.forum.utils.ForumPayloadUtils;
import jakarta.servlet.http.HttpServletRequest;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ForumEditAuthoritiesController {
    @Autowired
    private ForumGroupService forumGroupService;
    private ForumGroupRepository forumGroupRepository;

    @GetMapping("/edit_authorities")
    public String editPermissions(HttpServletRequest httpServletRequest, Model model) {
        ForumAuthUtils.includeAuthInformation(httpServletRequest, model);
        model.addAttribute("groups", forumGroupService.getGroups().values());

        return "/group/edit_authorities";
    }

    @GetMapping("/edit_authorities/{group}")
    public String editAuthorities(HttpServletRequest httpServletRequest, @PathVariable String group, Model model, RedirectAttributes redirectAttributes) {
        ForumAuthUtils.includeAuthInformation(httpServletRequest, model);
        if(forumGroupService.groupLoaded(group)) {
            model.addAttribute("modifiable", forumGroupService.getGroup(group));

            return "/group/edit_authorities";
        }else {
            redirectAttributes.addFlashAttribute("notification", true);
            redirectAttributes.addFlashAttribute("notificationMessage", "Group doesn't exists.");

            return "redirect:/edit_authorities";
        }
    }

    @PostMapping("/edit_authorities/{group}")
    @ResponseBody
    public ResponseEntity<String> addAuthority(@PathVariable String group, @RequestBody String request) {
        if(forumGroupService.groupLoaded(group)) {
            try {
                JSONObject requestJSON = ForumPayloadUtils.fromStringToJson(request);

                if(!forumGroupService.existsAuthority(group, requestJSON.getAsString("authority"))) {
                    if (forumGroupService.addAuthority(group, requestJSON.getAsString("authority"))) {
                        return ForumPayloadUtils.simpleResponse(HttpStatus.CREATED, "Added authority.");
                    } else {
                        return ForumPayloadUtils.simpleResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add authority.");
                    }
                }else {
                    return ForumPayloadUtils.simpleResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Authority already exists.");
                }
            } catch (Exception exception) {
                exception.printStackTrace();

                return ForumPayloadUtils.simpleResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add authority.");
            }
        }else {
            return ForumPayloadUtils.simpleResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Can't find group.");
        }
    }

    @DeleteMapping("/edit_authorities/{group}")
    @ResponseBody
    public ResponseEntity<String> deleteAuthority(@PathVariable String group, @RequestHeader("authority") String authority) {
        if(forumGroupService.groupLoaded(group)) {
            try {
                if(forumGroupService.existsAuthority(group, authority)) {
                    if(forumGroupService.deleteAuthority(group, authority)) {
                        return ForumPayloadUtils.simpleResponse(HttpStatus.OK, "Deleted authority.");
                    }else {
                        return ForumPayloadUtils.simpleResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete authority.");
                    }
                }else {
                    return ForumPayloadUtils.simpleResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Authority doesn't exists.");
                }
            }catch (Exception exception) {
                exception.printStackTrace();

                return ForumPayloadUtils.simpleResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete authority.");
            }
        }else {
            return ForumPayloadUtils.simpleResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Can't find group.");
        }
    }
}
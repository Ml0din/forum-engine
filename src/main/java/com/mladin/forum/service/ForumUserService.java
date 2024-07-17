package com.mladin.forum.service;

import com.mladin.forum.repository.ForumUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ForumUserService {
    @Autowired
    private ForumUserRepository forumUserRepository;

    public boolean existsByUsername(String username) {
        return forumUserRepository.existsByUsername(username);
    }

    public boolean existsUserByEmail(String email) {
        return forumUserRepository.existsByEmail(email);
    }
}

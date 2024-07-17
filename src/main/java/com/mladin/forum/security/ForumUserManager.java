package com.mladin.forum.security;

import com.mladin.forum.entity.ForumUserEntity;
import com.mladin.forum.repository.ForumUserRepository;
import com.mladin.forum.security.oauth2.ForumOAuth2User;
import com.mladin.forum.service.ForumGroupService;
import com.mladin.forum.utils.ForumUUIDPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ForumUserManager {
    public static Logger userManagerLogger = Logger.getLogger(ForumUserManager.class.getName());

    @Autowired
    private ForumUserRepository forumUserRepository;

    @Autowired
    private ForumGroupService forumGroupService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ForumUserEntity getEntityByEmail(String email) {
        return forumUserRepository.findByEmail(email).get();
    }

    public boolean userExistsByEmail(String email) {
        return forumUserRepository.existsByEmail(email);
    }

    public boolean matchPasswords(String password, String email) {
        return passwordEncoder.matches(password, forumUserRepository.findPasswordByEmail(email));
    }

    public void createUser(String username, String email, String password) {
        ForumUserEntity forumUserEntity = new ForumUserEntity(ForumUUIDPool.grabNewUUID(forumUserRepository).toString(), username, email, "member", passwordEncoder.encode(password), "none");
        forumUserRepository.save(forumUserEntity);

        userManagerLogger.info("Created user " + username + " with email " + email);
    }

    public ForumTokenUser authenticateUserPasswordToken(String email) {
        ForumUserEntity forumUserEntity = forumUserRepository.findByEmail(email).get();
        ForumTokenUser forumTokenUser = new ForumTokenUser(forumUserEntity.getID(), forumUserEntity.getUsername(), forumUserEntity.getEmail(), forumGroupService.getGroup(forumUserEntity.getGroup()), forumUserEntity.getPassword(), forumUserEntity.getAvatar(), forumGroupService.getGroup(forumUserEntity.getGroup()).getGrantedAuthorities());

        userManagerLogger.info("Authenticated with User Password Token user " + forumTokenUser.getUsername() + " with email " + email);
        return forumTokenUser;
    }

    public ForumOAuth2User authenticateOAuth2Token(String email) {
        if(forumUserRepository.existsByEmail(email)) {
            ForumUserEntity forumUserEntity = forumUserRepository.findByEmail(email).get();
            ForumOAuth2User forumOAuth2User = new ForumOAuth2User(forumUserEntity.getID(), forumUserEntity.getUsername(), forumUserEntity.getEmail(), forumGroupService.getGroup(forumUserEntity.getGroup()), forumUserEntity.getPassword(), forumUserEntity.getAvatar(), forumGroupService.getGroup(forumUserEntity.getGroup()).getGrantedAuthorities());

            userManagerLogger.info("Authenticated with OAuth2 Token user " + forumOAuth2User.getName() + " with email " + email);
            return forumOAuth2User;
        }else {
            throw new BadCredentialsException("Email doesn't exists in the database.");
        }
    }
}

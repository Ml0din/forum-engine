package com.mladin.forum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class ForumAuthProvider implements AuthenticationProvider {
    @Autowired
    private ForumUserManager forumUserManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (forumUserManager.userExistsByEmail(authentication.getName())) {
            if(forumUserManager.matchPasswords(authentication.getCredentials().toString(), authentication.getName())) {
                ForumTokenUser forumAuthTokenUser = forumUserManager.authenticateUserPasswordToken(authentication.getName());
                return new UsernamePasswordAuthenticationToken(forumAuthTokenUser, authentication.getCredentials(), forumAuthTokenUser.getAuthorities());
            }
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

package com.mladin.forum.security.oauth2;

import com.mladin.forum.security.ForumAuthType;
import com.mladin.forum.security.ForumAuthenticatedUser;
import com.mladin.forum.security.ForumGroup;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumOAuth2User implements OAuth2User, ForumAuthenticatedUser {
    private String id;
    private String username;
    private String email;
    private ForumGroup group;
    private String password;
    private String avatar;
    private ForumAuthType forumAuthType;
    private List<GrantedAuthority> authorities;

    public ForumOAuth2User(String id, String username, String email, ForumGroup group, String password, String avatar, List<GrantedAuthority> grantedAuthorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.group = group;
        this.password = password;
        this.avatar = avatar;
        this.forumAuthType = ForumAuthType.OAUTH_TOKEN;
        this.authorities = grantedAuthorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public ForumGroup group() {
        return group;
    }

    @Override
    public ForumAuthType authType() {
        return forumAuthType;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String avatar() {
        return avatar;
    }
}

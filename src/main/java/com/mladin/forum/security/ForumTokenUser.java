package com.mladin.forum.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ForumTokenUser implements UserDetails, ForumAuthenticatedUser {
    private String id;
    private String username;
    private String email;
    private ForumGroup group;
    private String password;
    private String avatar;
    private ForumAuthType forumAuthType;
    private List<GrantedAuthority> authorities;

    public ForumTokenUser(String id, String username, String email, ForumGroup group, String password, String avatar, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.group = group;
        this.password = password;
        this.avatar = avatar;
        this.forumAuthType = ForumAuthType.USERNAME_PASSWORD_TOKEN;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

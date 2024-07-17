package com.mladin.forum.security;

public interface ForumAuthenticatedUser {
    String id();
    String username();
    String email();
    ForumGroup group();
    ForumAuthType authType();
    String password();
    String avatar();
}

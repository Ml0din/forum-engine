package com.mladin.forum.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class ForumUserEntity {
    @Id
    @Column(name = "`id`")
    private String ID;

    @Column(name = "`username`")
    private String username;

    @Column(name = "`email`")
    private String email;

    @Column(name = "`group`")
    private String group;

    @Column(name = "`password`")
    private String password;

    @Column(name = "`avatar`")
    private String avatar;

    public ForumUserEntity() {}

    public ForumUserEntity(String ID, String username, String email, String group, String password, String avatar) {
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.group = group;
        this.password = password;
        this.avatar = avatar;
    }

    public String getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getGroup() {
        return group;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return this.avatar;
    }
}

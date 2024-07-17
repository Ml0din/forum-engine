package com.mladin.forum.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "`groups`")
public class ForumGroupEntity {
    @Id
    @Column(name = "`id`")
    private String id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`display`")
    private String display;

    @Column(name = "`authorities`")
    private byte[] authorities;

    public ForumGroupEntity() {}

    public ForumGroupEntity(String id, String name, String display, byte[] authorities) {
        this.id = id;
        this.name = name;
        this.display = display;
        this.authorities = authorities;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public byte[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(byte[] authorities) {
        this.authorities = authorities;
    }
}

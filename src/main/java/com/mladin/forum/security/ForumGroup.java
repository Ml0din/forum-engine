package com.mladin.forum.security;

import com.mladin.forum.entity.ForumGroupEntity;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

public class ForumGroup extends ForumGroupEntity implements Serializable {
    private int priority;
    private List<GrantedAuthority> grantedAuthorities;

    public ForumGroup(ForumGroupEntity forumGroupEntity, int priority, List<GrantedAuthority> grantedAuthorities) {
        super(forumGroupEntity.getId(), forumGroupEntity.getName(), forumGroupEntity.getDisplay(), forumGroupEntity.getAuthorities());
        this.priority = priority;
        this.grantedAuthorities = grantedAuthorities;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority() {
        this.priority = priority;
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        return this.grantedAuthorities;
    }
}

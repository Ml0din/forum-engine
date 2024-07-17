package com.mladin.forum.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "`category`")
public class ForumCategoryEntity {
    @Id
    @Column(name = "`id`")
    private String id;

    @Column(name = "`name`")
    private String name;

    @Transient
    private List<ForumSubcategoryEntity> subcategoryEntities;

    public ForumCategoryEntity() {}

    public ForumCategoryEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ForumSubcategoryEntity> getSubcategoryEntities() {
        return subcategoryEntities;
    }

    public void setSubcategoryEntities(List<ForumSubcategoryEntity> subcategoryEntities) {
        this.subcategoryEntities = subcategoryEntities;
    }
}

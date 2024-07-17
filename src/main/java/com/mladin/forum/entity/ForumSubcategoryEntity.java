package com.mladin.forum.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "`subcategory`")
public class ForumSubcategoryEntity {
    @Id
    @Column(name = "`id`")
    private String id;

    @Column(name = "`category`")
    private String category;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`description`")
    private String description;

    public ForumSubcategoryEntity() {}

    public ForumSubcategoryEntity(String id, String category, String name, String description) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

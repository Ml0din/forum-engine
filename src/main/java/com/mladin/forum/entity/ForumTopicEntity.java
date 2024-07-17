package com.mladin.forum.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "`topic`")
public class ForumTopicEntity {
    @Id
    @Column(name = "`id`")
    private String id;

    @Column(name = "`title`")
    private String title;

    @Column(name = "`content`")
    private String content;


    @Column(name = "`author`")
    private String author;

    @Column(name = "`date`")
    private String date;

    @Column(name = "`subcategory`")
    private String subcategory;

    @Transient
    private String authorName;

    public ForumTopicEntity() {}

    public ForumTopicEntity(String id, String title, String content, String author, String date, String subcategory) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
        this.subcategory = subcategory;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}

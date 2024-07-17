package com.mladin.forum.data;

public class ForumCreateGroupData {
    private String name;
    private String display;
    private String priority;

    public ForumCreateGroupData() {}

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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}

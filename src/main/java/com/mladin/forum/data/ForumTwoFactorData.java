package com.mladin.forum.data;

public class ForumTwoFactorData {
    private String code;
    public ForumTwoFactorData() {}
    public ForumTwoFactorData(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

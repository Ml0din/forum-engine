package com.mladin.forum.email.twofactor;

import java.util.UUID;

public class ForumEmailTwoFactorSession {
    private UUID sessionUUID;
    private int code;
    private ForumEmailTwoFactorType emailTwoFactorType;
    private Object persistentData;

    public ForumEmailTwoFactorSession(UUID sessionUUID, int code, ForumEmailTwoFactorType emailTwoFactorType, Object persistendData) {
        this.sessionUUID = sessionUUID;
        this.code = code;
        this.emailTwoFactorType = emailTwoFactorType;
        this.persistentData = persistendData;
    }

    public UUID getSessionUUID() {
        return sessionUUID;
    }

    public int getCode() {
        return code;
    }

    public ForumEmailTwoFactorType getEmailTwoFactorType() {
        return emailTwoFactorType;
    }

    public Object getPersistentData() {
        return this.persistentData;
    }
}

package com.mladin.forum.utils;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.logging.Logger;

public class ForumUUIDPool {
    public static Logger forumUUIDPoolLogger = Logger.getLogger("forumUUIDPool");

    public static UUID grabNewUUID(JpaRepository<?,String> repository) {
        UUID generatedUUID = UUID.randomUUID();
        while (repository.existsById(generatedUUID.toString())) {
            generatedUUID = UUID.randomUUID();
        }

        forumUUIDPoolLogger.info("Grabbed new UUID from pool: " + generatedUUID + ".");

        return generatedUUID;
    }

}

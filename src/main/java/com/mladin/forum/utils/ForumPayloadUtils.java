package com.mladin.forum.utils;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ForumPayloadUtils {
    private static JSONParser parser = new JSONParser();

    public static ResponseEntity<String> simpleResponse(HttpStatus status, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);

        return ResponseEntity.status(status).body(jsonObject.toJSONString());
    }

    public static JSONObject fromStringToJson(String json) throws Exception {
        return (JSONObject) parser.parse(json);
    }
}

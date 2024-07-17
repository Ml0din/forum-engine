package com.mladin.forum.utils;

import java.util.regex.Pattern;

public class ForumPatternVerifier {
    public static Pattern namePattern = Pattern.compile("[a-zA-Z0-9]+");
    public static Pattern numberPattern = Pattern.compile("[0-9&&[^a-zA-Z]]+");
    public static Pattern emailPattern = Pattern.compile("[a-zA-Z0-9\\p{Punct}]+@gmail.com");
    public static Pattern passwordPattern = Pattern.compile("[a-zA-Z0-9\\p{Punct}]+");
    public static boolean verifyName(String name)  {
        return namePattern.matcher(name).matches();
    }

    public static boolean verifyNumber(String number)  {
        return numberPattern.matcher(number).matches() && number.length() < 7;
    }

    public static boolean verifyEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

    public static boolean verifyPassword(String password) {
        return passwordPattern.matcher(password).matches();
    }
}

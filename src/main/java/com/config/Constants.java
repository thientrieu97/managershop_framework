package com.config;

/**
 * Application constants.
 */
public final class Constants {
    //AWS S3
    public static final Integer NAME_LEFT_LIMIT = 48;
    public static final Integer NAME_RIGHT_LIMIT = 122;
    public static final Integer TARGET_STRING_LENGTH = 20;
    public static final Integer CHARACTER_57 = 57;
    public static final Integer CHARACTER_A_UPPER_CASE = 65;
    public static final Integer CHARACTER_Z = 90;
    public static final Integer CHARACTER_A_LOWER_CASE = 97;

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    private Constants() {}
}

package com.scala.expandroidsdk;

/**
 * Created by Cesar Oyarzun on 11/6/15.
 */
public class Utils {
    public static final String MESSAGE = "message";
    public static final String TYPE = "type";
    public static final String REQUEST = "request";
    public static final String GET_CURRENT_EXPERIENCE = "getCurrentExperience";
    public static final String CHANNEL = "channel";
    public static final String RESPONSE = "response";
    public static final String BROADCAST = "broadcast";
    public static final String GET_CURRENT_DEVICE = "getCurrentDevice";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TYP = "typ";
    public static final String JWT = "JWT";
    public static final String UUID = "uuid";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ORG = "org";

    public enum SOCKET_CHANNELS {
        SYSTEM ("system"),
        ORGANIZATION ("organization"),
        LOCATION ("location"),
        EXPERIENCE ("experience");
        private final String channel;
        SOCKET_CHANNELS(String channel) {
            this.channel = channel;
        }
    }

    /**
     * Create Enum from string
     * @param text
     * @return
     */
    public static SOCKET_CHANNELS fromString(String text) {
        if (text != null) {
            for (SOCKET_CHANNELS type : SOCKET_CHANNELS.values()) {
                if (text.equalsIgnoreCase(type.channel)) {
                    return type;
                }
            }
        }
        return null;
    }
}

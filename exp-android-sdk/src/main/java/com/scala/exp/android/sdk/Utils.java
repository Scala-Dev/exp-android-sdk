package com.scala.exp.android.sdk;

/**
 * Created by Cesar Oyarzun on 11/6/15.
 */
public class Utils {
    public static final String CONSUMER_APP_UUID = "consumerAppUuid";
    public static final String CONSUMER_APP = "consumerApp";
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
    public static final String PAYLOAD = "payload";
    public static final String FLING = "fling";
    public static final String ONLINE = "online";
    public static final String OFFLINE = "offline";
    public static final String ORGANIZATION = "organization";
    public static final String SECRET = "secret";
    public static final String DEVICE_UUID = "deviceUuid";
    public static final String DEVICE = "device";
    public static final String NETWORK_UUID = "networkUuid";
    public static final String API_KEY = "apiKey";
    public static final String HOST = "host";
    public static final String ENABLE_EVENTS = "enableEvents";
    public static final String SUBSCRIBED = "subscribed";
    public static final String UPDATE = "update";
    public static final String ERROR = "error";
    public static final String LOCATION_UUID = "location.uuid";
    public static final String KEY = "key";
    public static final String ZONES = "zones";
    public static final String LOCATION = "location";
    public static final String EXPERIENCE = "experience";
    public static final String LOCATION_ZONES_KEY = "location.zones.key";
    public static final String IDENTIFY = "identify";
    public static final String RESULTS = "results";
    public static final String TOTAL = "total";
    public static final String SUBTYPE = "subtype";
    public static final String GROUP = "group";
    public static final String VALUE = "value";

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

    public enum CONTENT_TYPES {
        APP ("scala:content:app"),
        FILE ("scala:content:file"),
        FOLDER ("scala:content:folder"),
        URL ("scala:content:url"),
        UNKNOW ("");
        private final String subtype;
        CONTENT_TYPES(String subtype) {
            this.subtype = subtype;
        }
    }

    /**
     * Create Enum from string
     * @param text
     * @return
     */
    public static SOCKET_CHANNELS getSocketEnum(String text) {
        if (text != null) {
            for (SOCKET_CHANNELS type : SOCKET_CHANNELS.values()) {
                if (text.equalsIgnoreCase(type.channel)) {
                    return type;
                }
            }
        }
        return null;
    }

    /**
     * Create Enum from string
     * @param text
     * @return
     */
    public static CONTENT_TYPES getContentTypeEnum(String text) {
        if (text != null) {
            for (CONTENT_TYPES type : CONTENT_TYPES.values()) {
                if (text.equalsIgnoreCase(type.subtype)) {
                    return type;
                }
            }
        }
        return null;
    }
}

package com.scala.exp.android.sdk.model;

/**
 * Created by Cesar Oyarzun on 1/22/16.
 */
public class Network {
    private String uuid;
    private String host;
    private String isPrimary;

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

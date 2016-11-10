package com.scala.exp.android.sdk.model;

import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.channels.IChannel;

import java.util.Map;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public abstract class AbstractModel implements IExpModel {

    public static final String CONSUMER = "consumer";
    public static final String SYSTEM = "system";
    protected LinkedTreeMap properties = null;


    public Object get(String path) {
        Object o = null;
        String[] keys = path.split("\\.");
        LinkedTreeMap subdoc = this.properties;
        for (int i = 0; i < keys.length; i++) {
            if(i == keys.length -1){
                o = subdoc.get(keys[i]);
            }
            if(subdoc.get(keys[i]) instanceof LinkedTreeMap){
                subdoc = (LinkedTreeMap) subdoc.get(keys[i]);
            }
        }
        return o;
    }

    public String getString(String path) {
        final Object obj = get(path);
        return obj != null ? String.valueOf(obj) : null;
    }

    public Integer getInteger(String path) {
        final Object obj = get(path);
        if (obj instanceof Integer) return (Integer) obj;

        return obj != null ? Integer.valueOf(String.valueOf(obj)) : null;
    }

    public Boolean getBoolean(String path) {
        final Object obj = get(path);
        if (obj instanceof Boolean) return (Boolean) obj;

        return obj != null ? Boolean.valueOf(String.valueOf(obj)) : null;
    }

    public void setProperties(LinkedTreeMap properties) {
        this.properties = properties;
    }

    public String getUuid() {
        return getString(Utils.UUID);
    }

    @Override
    public String toString() {
        return this.properties.toString();
    }

    /**
     * Get Channel
     * @return
     */
    public IChannel getChannel(){
       return  Exp.getChannel(getChannelName(),false,false);
    }
    
    /**
     * Get Channel
     * @param system
     * @param consumer
     * @return
     */
    public IChannel getChannel(boolean system, boolean consumer){
        return  Exp.getChannel(getChannelName(),system,consumer);
    }

    protected String getChannelName(){
        return this.getUuid();
    }

    public Map getDocument(){
        return this.properties;
    }
}

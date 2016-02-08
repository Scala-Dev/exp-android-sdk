package com.scala.exp.android.sdk.channels;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import rx.Subscriber;

/**
 * Created by Cesar Oyarzun on 11/6/15.
 */
public interface IChannel {

    void onResponse(JSONObject response) throws JSONException;
    void onRequest(JSONObject request) throws JSONException;
    void onBroadCast(JSONObject broadcast) throws JSONException;
    void request(Map<String,String> message, Subscriber callback) throws JSONException;
    void broadcast(String name,Map<String,String> message);
    void listen(Map<String,String> message,Subscriber callback);
    void response(Map<String,String> message,Subscriber callback);
    void fling(String uuid);
}

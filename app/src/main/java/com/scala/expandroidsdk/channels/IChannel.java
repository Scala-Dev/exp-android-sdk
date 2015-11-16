package com.scala.expandroidsdk.channels;

import com.google.gson.JsonObject;
import com.scala.expandroidsdk.observer.ExpObservable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Cesar Oyarzun on 11/6/15.
 */
public interface IChannel {

    void onResponse(JSONObject response) throws JSONException;
    void onRequest(JSONObject request) throws JSONException;
    void onBroadCast(JSONObject broadcast) throws JSONException;
    void request(Map<String,String> message, Subscriber callback) throws JSONException;
    void broadcast(Map<String,String> message);
    void listen(Map<String,String> message,Subscriber callback);
    void response(Map<String,String> message,Subscriber callback);
    void fling(String uuid);
}

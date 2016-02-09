package com.scala.exp.android.sdk.channels;

import com.scala.exp.android.sdk.observer.ExpObservable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.ReplaySubject;

/**
 * Created by Cesar Oyarzun on 11/6/15.
 */
public interface IChannel {
    void onBroadCast(JSONObject broadcast) throws JSONException;
    void broadcast(String name,Map<String,Object> message,int timeout);
    ReplaySubject<Object> listen(String name, Subscriber callback);
    void fling(Map<String,Object> payload);
}

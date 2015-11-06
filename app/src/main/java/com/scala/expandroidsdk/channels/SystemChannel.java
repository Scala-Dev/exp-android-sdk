package com.scala.expandroidsdk.channels;


import com.scala.expandroidsdk.Utils;
import com.scala.expandroidsdk.observer.ExpObservable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.socket.client.Socket;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Cesar Oyarzun on 11/4/15.
 */
public class SystemChannel implements IChannel {

    public static final String SYSTEM = "system";

    private Socket socket;
    public Map<String,Subscriber> request = new HashMap<>();
    public Map<String,Subscriber> listeners = new HashMap<>();
    public Map<String,Subscriber> responders = new HashMap<>();
    public SystemChannel(Socket socket){
        this.socket = socket;
    }

    @Override
    public void onResponse(JSONObject response) throws JSONException {
        String id = response.getString(Utils.ID);
        Subscriber subscriber = request.get(id);
        subscriber.onNext(response);
        subscriber.onCompleted();
    }

    @Override
    public void onRequest(JSONObject request) {

    }

    @Override
    public void onBroadCast(JSONObject message) throws JSONException {
        message.put(Utils.TYPE,Utils.BROADCAST);
        message.put(Utils.CHANNEL,SYSTEM);
        this.socket.emit(Utils.MESSAGE,message);
    }

    @Override
    public void request(Map<String,String> message, Subscriber callback) throws JSONException {
        String uuid = UUID.randomUUID().toString();
        message.put(Utils.ID,uuid);
        message.put(Utils.CHANNEL, SYSTEM);
        request.put(uuid, callback);
        socket.emit(Utils.MESSAGE, new JSONObject(message));
    }

    @Override
    public void broadcast() {

    }

    @Override
    public void listen(Map<String,String> message,Subscriber callback) {
        String name = message.get(Utils.NAME);
        listeners.put(name,callback);
    }

    @Override
    public void response(Map<String,String> message,Subscriber callback) {
        String name = message.get(Utils.NAME);
        responders.put(name,callback);
    }

    @Override
    public void fling(String uuid) {

    }
}

package com.scala.expandroidsdk.channels;


import com.scala.expandroidsdk.observer.ExpObservable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.socket.client.Socket;
import rx.Subscriber;

/**
 * Created by Cesar Oyarzun on 11/4/15.
 */
public class OrganizationChannel implements IChannel {

    private Socket socket;

    public OrganizationChannel(Socket socket){
        this.socket = socket;
    }


    @Override
    public void onResponse(JSONObject response) throws JSONException {

    }

    @Override
    public void onRequest(JSONObject request) {

    }

    @Override
    public void onBroadCast(JSONObject broadcast) throws JSONException {

    }

    @Override
    public void request(Map<String, String> message, Subscriber callback) throws JSONException {

    }

    @Override
    public void broadcast() {

    }

    @Override
    public void listen(Map<String, String> message, Subscriber callback) {

    }

    @Override
    public void response(Map<String, String> message, Subscriber callback) {

    }

    @Override
    public void fling(String uuid) {

    }
}

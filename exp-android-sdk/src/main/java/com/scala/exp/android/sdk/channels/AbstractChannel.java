package com.scala.exp.android.sdk.channels;


import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.SocketManager;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.model.Auth;
import com.scala.exp.android.sdk.model.Message;
import com.scala.exp.android.sdk.observer.ExpObservable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;

/**
 * Created by Cesar Oyarzun on 11/16/15.
 */
public abstract class AbstractChannel implements IChannel {
    protected SocketManager socketManager;
    protected String channelName = "";
    protected boolean system = false;
    protected boolean consumerApp = false;
    public String channelID = "";
    public Map<String, List<Subscriber>> listeners = new HashMap<>();
    private static final String LOG_TAG = AbstractChannel.class.getSimpleName();
    private static final int TIMEOUT = 2000;


    protected void setChannel(String channel){
        this.channelName = channel;
    }
    protected void setSystem(boolean system){
        this.system = system;
    }
    protected void setConsumerApp(boolean consumerApp){
        this.consumerApp = consumerApp;
    }

    @Override
    public void onBroadCast(JSONObject response) throws JSONException {
        String name = response.getString(Utils.NAME);
        if(this.listeners.containsKey(name) && this.listeners.get(name)!=null){
            List<Subscriber> subscriberList = this.listeners.get(name);
            for (Subscriber subscriber : subscriberList) {
                Object payload = response.get(Utils.PAYLOAD);
                subscriber.onNext(payload);
                subscriber.onCompleted();
            }

        }
    }

    @Override
    public void broadcast(String name , Map<String,Object> payload, int timeout) {
        HashMap mapPayload = (HashMap) payload;
        Map<String,Object> message = new HashMap<>();
        message.put(Utils.NAME,name);
        message.put(Utils.CHANNEL, generateId());
        message.put(Utils.PAYLOAD,mapPayload.clone());
        broadCast(message,timeout).then(new Subscriber<Message>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, "EXP Error broadcast " + e.getMessage());}
            @Override
            public void onNext(Message message) {
                Log.d(LOG_TAG, "EXP broadcast response" + message);}
        });

    }

    @Override
    public ReplaySubject<Object> listen(String name, Subscriber callback) {
        List<Subscriber> subList = createSubList(name, callback);
        listeners.put(name, subList);
        return socketManager.subscribe(generateId());
    }



    @Override
    public void fling(Map<String,Object> payload) {
        HashMap mapPayload = (HashMap) payload;
        Map<String,Object> message = new HashMap<>();
        message.put(Utils.CHANNEL,this.channelID);
        message.put(Utils.NAME, Utils.FLING);
        message.put(Utils.PAYLOAD, mapPayload.clone());
        broadCast(message,TIMEOUT).then(new Subscriber<Message>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, "EXP Error fling " + e.getMessage());
            }

            @Override
            public void onNext(Message message) {
                Log.d(LOG_TAG, "EXP broadcast response" + message);
            }
        });
    }

    /**
     * Broad Cast request
     * @param options,timeout
     * @return
     */
    private static ExpObservable<Message> broadCast(Map<String,Object> options, int timeout){
        Observable<Message> broadcastObservable = AppSingleton.getInstance().getEndPoint().broadCast(options,timeout)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Message>(broadcastObservable);
    }


    /**
     * Generate Channel ID
     * @return
     */
    public String generateId(){
        if(channelID.isEmpty()){
           if( AppSingleton.getInstance().getAuth()!=null){
               Auth user = AppSingleton.getInstance().getAuth();
               String organization = user.getIdentity().getOrganization();
               List<Object> listOptions= new ArrayList<Object>();
               listOptions.add(organization);
               listOptions.add(this.channelName);
               int systemInt = this.system ? 1 : 0;
               int consumerAppInt = this.consumerApp ? 1:0;
               listOptions.add(systemInt);
               listOptions.add(consumerAppInt);
               Gson gson = new Gson();
               String jsonOptions = gson.toJson(listOptions);
               // encode data on your side using BASE64
               String encodeToString = new String(Base64.encode(jsonOptions.getBytes(), Base64.NO_WRAP));
               Log.d(LOG_TAG, "EXP Generate ID :" + encodeToString);
               channelID = encodeToString;
           }
        }
        return channelID;
    }

    @Override
    public void identify() {
        Map<String,Object> message = new HashMap<>();
        message.put(Utils.CHANNEL,this.channelID);
        message.put(Utils.NAME, Utils.IDENTIFY);
        broadCast(message,TIMEOUT).then(new Subscriber<Message>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, "EXP Error identify " + e.getMessage());}
            @Override
            public void onNext(Message message){
                Log.d(LOG_TAG, "EXP identify response" + message);}
        });
    }

    /**
     * Create Subscriber list
     * @param name
     * @param callback
     * @return
     */
    private List<Subscriber> createSubList(String name, Subscriber callback){
        List<Subscriber> subscribersList = null;
        if(this.listeners.containsKey(name) && this.listeners.get(name)!=null){
            subscribersList = this.listeners.get(name);
        }else {
            subscribersList = new ArrayList<>();
            this.listeners.put(name,subscribersList);
        }
        subscribersList.add(callback);
        return subscribersList;
    }


}

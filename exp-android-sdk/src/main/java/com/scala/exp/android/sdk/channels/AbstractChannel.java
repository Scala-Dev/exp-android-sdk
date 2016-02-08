package com.scala.exp.android.sdk.channels;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.SocketManager;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.model.Data;
import com.scala.exp.android.sdk.model.Message;
import com.scala.exp.android.sdk.model.SearchResults;
import com.scala.exp.android.sdk.observer.ExpObservable;

import io.socket.client.Socket;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Cesar Oyarzun on 11/16/15.
 */
public abstract class AbstractChannel implements IChannel {
    protected SocketManager socketManager;
    protected String CHANNEL = "";
    protected int system = 0;
    protected int consumerApp = 0;
    public String channelID = "";
    public Map<String,Subscriber> request = new HashMap<>();
    public Map<String,Subscriber> listeners = new HashMap<>();
    public Map<String,Subscriber> responders = new HashMap<>();

    protected void setChannel(String channel){
        this.CHANNEL = channel;
    }
    protected void setSystem(int system){
        this.system = system;
    }
    protected void setConsumerApp(int consumerApp){
        this.consumerApp = consumerApp;
    }

    @Override
    public void onResponse(JSONObject response) throws JSONException {
        String id = response.getString(Utils.ID);
        if( id != null){
            if ( request.get(id) != null){
                Subscriber subscriber = request.get(id);
                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        }
    }

    @Override
    public void onRequest(JSONObject response) throws JSONException {
        String name = response.getString(Utils.NAME);
        if(this.responders.containsKey(name) && this.responders.get(name)!=null){
            Subscriber subscriber = this.responders.get(name);
            Object payload = response.get(Utils.PAYLOAD);
            subscriber.onNext(payload);
            subscriber.onCompleted();
        }
    }

    @Override
    public void onBroadCast(JSONObject response) throws JSONException {
        String name = response.getString(Utils.NAME);
        if(this.listeners.containsKey(name) && this.listeners.get(name)!=null){
            Subscriber subscriber = this.listeners.get(name);
            Object payload = response.get(Utils.PAYLOAD);
            subscriber.onNext(payload);
            subscriber.onCompleted();
        }
    }

    @Override
    public void request(Map<String,String> message, Subscriber callback) throws JSONException {
        String uuid = java.util.UUID.randomUUID().toString();
        message.put(Utils.ID,uuid);
        message.put(Utils.CHANNEL, this.CHANNEL);
        request.put(uuid, callback);
//        socket.emit(Utils.MESSAGE, new JSONObject(message));
    }

    @Override
    public void broadcast(String name ,Map<String,String> payload) {
        Map<String,Object> message = new HashMap<>();
        message.put(Utils.NAME,name);
        message.put(Utils.CHANNEL, generateId());
        message.put(Utils.PAYLOAD,payload);
        broadCast(message);
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
        Map<String,String> mapFling = new HashMap<>();
        mapFling.put(Utils.UUID,uuid);
        Map<String,Object> message = new HashMap<>();
        message.put(Utils.TYPE,Utils.BROADCAST);
        message.put(Utils.CHANNEL,this.CHANNEL);
        message.put(Utils.NAME, Utils.FLING);
        message.put(Utils.PAYLOAD, mapFling);
//        this.socket.emit(Utils.MESSAGE,new JSONObject(message));
    }

    /**
     * Get Data by group,key
     * @param options
     * @return
     */
    public static ExpObservable<Message> broadCast(Map<String,Object> options){
        Observable<Message> broadcastObservable = AppSingleton.getInstance().getEndPoint().broadCast(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Message>(broadcastObservable);
    }


    public String generateId(){
        if(!channelID.isEmpty()){

        }
        return channelID;
    }
}

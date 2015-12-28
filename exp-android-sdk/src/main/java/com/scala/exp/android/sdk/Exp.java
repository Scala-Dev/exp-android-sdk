package com.scala.exp.android.sdk;

import com.scala.exp.android.sdk.channels.IChannel;
import com.scala.exp.android.sdk.model.*;
import com.scala.exp.android.sdk.observer.ExpObservable;

import org.json.JSONException;

import java.lang.*;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Cesar Oyarzun on 10/28/15.
 */
public class Exp {

    private static Runtime runtime = new Runtime();
    protected static SocketManager socketManager = new SocketManager();

    /**
     ** Start EXP connection
     * @param host
     * @param uuid
     * @param secret
     * @return
     */
    public static Observable<Boolean> start(String host,String uuid,String secret){
        return  runtime.start(host,uuid,secret);
    }

    /**
     * Start EXP connection
     * @param host
     * @param username
     * @param password
     * @return
     */
    public static Observable<Boolean> start(String host, String username, String password, String organization){
        return runtime.start(host,username,password,organization);
    }

    /**
     * Start EXP connection
     * @param options
     * @return
     */
    public static Observable<Boolean> start(Map<String,String> options){
        return runtime.start(options);
    }

    /**
     * Login observable TODO make this ExpObservable
     * @param options
     * @return
     */
    public static Observable<Token> login(Map<String, String> options){
        Observable<Token> observable = AppSingleton.getInstance().getEndPoint().login(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    /**
     * Get Things by UUID
     * @param uuid
     * @return
     */
    public static ExpObservable<Thing> getThing(String uuid){
        Observable<Thing> thingObservable = AppSingleton.getInstance().getEndPoint().getThing(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Thing>(thingObservable);
    }

    /**
     * Find Things by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable<SearchResults<Thing>> findThings(Map<String,String> options){
        Observable<SearchResults<Thing>> resultThingObservable = AppSingleton.getInstance().getEndPoint().findThings(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<SearchResults<Thing>>(resultThingObservable);
    }

    /**
     * Get Feed by UUID
     * @param uuid
     * @return
     */
    public static ExpObservable<Feed> getFeed(String uuid){
        Observable<Feed> observable = AppSingleton.getInstance().getEndPoint().getFeed(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Feed>(observable);
    }

    /**
     * Find Feeds by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable<SearchResults<Feed>> findFeeds(Map<String,String> options){
        Observable<SearchResults<Feed>> observable = AppSingleton.getInstance().getEndPoint().findFeeds(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<SearchResults<Feed>>(observable);
    }

    /**
     * Get Device by UUID
     * @param uuid
     * @return
     */
    public static ExpObservable<Device> getDevice(String uuid){
        Observable<Device> deviceObservable = AppSingleton.getInstance().getEndPoint().getDevice(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Device>(deviceObservable);
    }

    /**
     * Find Devices by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable<SearchResults<Device>> findDevices(Map<String,String> options){
        Observable<SearchResults<Device>> deviceObservable = AppSingleton.getInstance().getEndPoint().findDevices(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<SearchResults<Device>>(deviceObservable);
    }

    /**
     * Get Experience By UUID
     * @param uuid
     * @return
     */
    public static ExpObservable<Experience> getExperience(String uuid){
        Observable<Experience> experienceObservable = AppSingleton.getInstance().getEndPoint().getExperience(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Experience>(experienceObservable);
    }

    /**
     * Find Experiences by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable<SearchResults<Experience>> findExperiences(Map<String,String> options){
        Observable<SearchResults<Experience>> experienceObservable = AppSingleton.getInstance().getEndPoint().findExperiences(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<SearchResults<Experience>>(experienceObservable);
    }

    /**
     * Get Location by UUID
     * @param uuid
     * @return
     */
    public static ExpObservable<Location> getLocation(String uuid){
        Observable<Location> locationObservable = AppSingleton.getInstance().getEndPoint().getLocation(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Location>(locationObservable);
    }

    /**
     * Find Location by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable<SearchResults<Location>> findLocations(Map<String,String> options){
        Observable<SearchResults<Location>> locationObservable = AppSingleton.getInstance().getEndPoint().findLocations(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<SearchResults<Location>>(locationObservable);
    }

    /**
     * Get Content Node by UUID
     * @param uuid
     * @return
     */
    public static ExpObservable<ContentNode> getContentNode(String uuid){
        Observable<ContentNode> contentNodeObservable = AppSingleton.getInstance().getEndPoint().getContentNode(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<ContentNode>(contentNodeObservable);
    }

    /**
     * Get Content Node by UUID
     * @param uuid
     * @return
     */
    public static ExpObservable<ContentNode> getContent(String uuid){
        return getContentNode(uuid);
    }

    /**
     * Find ContentNodes by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable<SearchResults<ContentNode>> findContentNodes(Map<String,String> options){
        Observable<SearchResults<ContentNode>> contentNodeObservable = AppSingleton.getInstance().getEndPoint().findContentNodes(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<SearchResults<ContentNode>>(contentNodeObservable);
    }

    /**
     * Find ContentNodes by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable<SearchResults<ContentNode>> findContent(Map<String,String> options){
        return findContentNodes(options);
    }

    /**
     * Get Data by group,key
     * @param group
     * @param key
     * @return
     */
    public static ExpObservable<Data> getData(String group,String key){
        Observable<Data> dataObservable = AppSingleton.getInstance().getEndPoint().getData(group, key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Data>(dataObservable);
    }

    /**
     * Get Data by group,key
     * @param options
     * @return
     */
    public static ExpObservable<SearchResults<Data>> findData(Map<String,String> options){
        Observable<SearchResults<Data>> dataObservable = AppSingleton.getInstance().getEndPoint().findData(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<SearchResults<Data>>(dataObservable);
    }

    /**
     * Get Current Experience from event bus
     * @param subscriber
     */
    public static void getCurrentExperience(Subscriber subscriber){
        try {
            socketManager.getCurrentExperience(subscriber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Current Device from event bus
     * @param subscriber
     */
    public static void getCurrentDevice(Subscriber subscriber){
        try {
             socketManager.getCurrentDevice(subscriber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Channel
     * @param channel
     * @return
     */
    public static IChannel getChannel(Utils.SOCKET_CHANNELS channel){
        return socketManager.getChannel(channel);
    }

    /**
     * Stop EXP connection
     */
    public static void stop(){
         runtime.stop();
    }

    /**
     * Get Channel
     * @param channel
     * @return
     */
    public static IChannel getChannel(String channel){
        return socketManager.getChannel(channel);
    }

    /**
     * Connection to socket manager
     * @param name
     * @param subscriber
     */
    public static void connection(String name,Subscriber subscriber){
        socketManager.connection(name,subscriber);
    }
}

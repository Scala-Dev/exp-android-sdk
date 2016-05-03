package com.scala.exp.android.sdk;

import android.util.Log;

import com.scala.exp.android.sdk.channels.IChannel;
import com.scala.exp.android.sdk.model.*;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Cesar Oyarzun on 10/28/15.
 */
public class Exp {

    private static Runtime runtime = new Runtime();
    protected static SocketManager socketManager = new SocketManager();
    protected static Map<String,Subscriber> authConnection = new HashMap<>();

    /**
     ** Start EXP connection
     * @param host
     * @param uuid
     * @param secret
     * @return
     */
    public static Observable<Boolean> start(String host,String uuid,String secret){
        Map<String,Object> startOptions = new HashMap<>();
        startOptions.put(Utils.HOST,host);
        startOptions.put(Utils.DEVICE_UUID,uuid);
        startOptions.put(Utils.SECRET,secret);
        return  runtime.start(startOptions);
    }

    /**
     * Start EXP connection
     * @param host
     * @param username
     * @param password
     * @return
     */
    public static Observable<Boolean> start(String host, String username, String password, String organization){
        Map<String,Object> startOptions = new HashMap<>();
        startOptions.put(Utils.HOST,host);
        startOptions.put(Utils.USERNAME,username);
        startOptions.put(Utils.PASSWORD,password);
        startOptions.put(Utils.ORGANIZATION,organization);
        return runtime.start(startOptions);
    }

    /**
     * Start EXP connection
     * @param options
     * @return
     */
    public static Observable<Boolean> start(Map<String,Object> options) {
        return runtime.start(options);
    }

    /**
     * Login observable
     * @param options
     * @return
     */
    public static Observable<Auth> login(Map<String, Object> options){
        Observable<Auth> observable = AppSingleton.getInstance().getEndPoint().login(options)
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
    public static ExpObservable<SearchResults<Thing>> findThings(Map<String,Object> options){
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
    public static ExpObservable<SearchResults<Feed>> findFeeds(Map<String,Object> options){
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
    public static ExpObservable<SearchResults<Device>> findDevices(Map<String,Object> options){
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
    public static ExpObservable<SearchResults<Experience>> findExperiences(Map<String,Object> options){
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
    public static ExpObservable<SearchResults<Location>> findLocations(Map<String,Object> options){
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
    public static ExpObservable<Content> getContent(String uuid){
        Observable<Content> contentNodeObservable = AppSingleton.getInstance().getEndPoint().getContent(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Content>(contentNodeObservable);
    }


    /**
     * Get Content Node by UUID
     * @param uuid
     * @return
     * @deprecated
     * Use getContent() instead
     */
    @Deprecated
    public static ExpObservable<ContentNode> getContentNode(String uuid){
        Observable<ContentNode> contentNodeObservable = AppSingleton.getInstance().getEndPoint().getContentNode(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<ContentNode>(contentNodeObservable);
    }

    /**
     * Find Content by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable<SearchResults<Content>> findContent(Map<String,Object> options){
        Observable<SearchResults<Content>> contentNodeObservable = AppSingleton.getInstance().getEndPoint().findContent(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<SearchResults<Content>>(contentNodeObservable);
    }

    /**
     * Find ContentNodes by Limit,Skip,Sort
     * @param options
     * @return
     * @deprecated
     * Use findContent() instead
     */
    @Deprecated
    public static ExpObservable<SearchResults<ContentNode>> findContentNodes(Map<String,Object> options){
        Observable<SearchResults<ContentNode>> contentNodeObservable = AppSingleton.getInstance().getEndPoint().findContentNodes(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<SearchResults<ContentNode>>(contentNodeObservable);
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
    public static ExpObservable<SearchResults<Data>> findData(Map<String,Object> options){
        Observable<SearchResults<Data>> dataObservable = AppSingleton.getInstance().getEndPoint().findData(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<SearchResults<Data>>(dataObservable);
    }

    /**
     * Respond method to broadcast
     * @param options
     * @return
     */
    public static ExpObservable<Message> respond(Map<String,Object> options){
        Observable<Message> respondObservable = AppSingleton.getInstance().getEndPoint().respond(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Message>(respondObservable);
    }

    /**
     * RefreshToken observable
     * @return
     */
    public static Observable<Auth> refreshToken(){
        return AppSingleton.getInstance().getEndPoint().refreshToken();
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
    public static IChannel getChannel(String channel,boolean system,boolean consumerApp){
        return socketManager.getChannel(channel,system,consumerApp);
    }

    /**
     * Connection to socket manager
     * @param name
     * @param subscriber
     */
    public static void connection(String name,Subscriber subscriber){
        socketManager.connection(name,subscriber);
    }

    /**
     * Return socket connection status
     * @return
     */
    public static boolean isConnected(){
        return socketManager.isConnected();
    }

    /**
     * Get Auth object
     * @return
     */
    public static Auth getAuth(){
        return AppSingleton.getInstance().getAuth();
    }

    /**
     * Connection to socket manager
     * @param name
     * @param subscriber
     */
    public static void on(String name,Subscriber subscriber){
        authConnection.put(name, subscriber);
    }
}

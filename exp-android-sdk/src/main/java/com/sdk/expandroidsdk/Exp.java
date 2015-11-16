package com.sdk.expandroidsdk;

import com.sdk.expandroidsdk.channels.IChannel;
import com.sdk.expandroidsdk.model.ContentNode;
import com.sdk.expandroidsdk.model.Data;
import com.sdk.expandroidsdk.model.Device;
import com.sdk.expandroidsdk.model.Experience;
import com.sdk.expandroidsdk.model.Location;
import com.sdk.expandroidsdk.model.ResultData;
import com.sdk.expandroidsdk.model.ResultDevice;
import com.sdk.expandroidsdk.model.ResultExperience;
import com.sdk.expandroidsdk.model.ResultLocation;
import com.sdk.expandroidsdk.model.ResultThing;
import com.sdk.expandroidsdk.model.Thing;
import com.sdk.expandroidsdk.model.Token;
import com.sdk.expandroidsdk.observer.ExpObservable;

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
    public static Observable start(String host,String uuid,String secret){
        return  runtime.start(host,uuid,secret);
    }

    /**
     * Start EXP connection
     * @param host
     * @param username
     * @param password
     * @return
     */
    public static Observable start(String host, String username, String password, String organization){
        return runtime.start(host,username,password,organization);
    }

    /**
     * Start EXP connection
     * @param host
     * @param username
     * @param password
     * @return
     */
    public static Observable start(Map<String,String> options){
        return runtime.start(options);
    }

    /**
     * Login observable TODO make this ExpObservable
     * @param options
     * @return
     */
    public static Observable login(Map<String, String> options){
        Observable<Token> thingObservable = AppSingleton.getInstance().getEndPoint().login(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return thingObservable;
    }

    /**
     * Get Things by UUID
     * @param uuid
     * @return
     */
    public static ExpObservable getThing(String uuid){
        Observable<Thing> thingObservable = AppSingleton.getInstance().getEndPoint().getThing(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(thingObservable);
    }

    /**
     * Find Things by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable  findthings(Map<String,String> options){
        Observable<ResultThing> resultThingObservable = AppSingleton.getInstance().getEndPoint().findThings(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(resultThingObservable);
    }

    /**
     * Get Device by UUID
     * @param uuid
     * @return
     */
    public static ExpObservable getDevice(String uuid){
        Observable<Device> deviceObservable = AppSingleton.getInstance().getEndPoint().getDevice(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(deviceObservable);
    }

    /**
     * Find Devices by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable  findDevices(Map<String,String> options){
        Observable<ResultDevice> deviceObservable = AppSingleton.getInstance().getEndPoint().findDevices(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(deviceObservable);
    }

    /**
     * Get Experience By UUID
     * @param uuid
     * @return
     */
    public static ExpObservable getExperience(String uuid){
        Observable<Experience> experienceObservable = AppSingleton.getInstance().getEndPoint().getExperience(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(experienceObservable);
    }

    /**
     * Find Experiences by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable  findExperiences(Map<String,String> options){
        Observable<ResultExperience> experienceObservable = AppSingleton.getInstance().getEndPoint().findExperiences(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(experienceObservable);
    }

    /**
     * Get Location by UUID
     * @param uuid
     * @return
     */
    public static ExpObservable getLocation(String uuid){
        Observable<Location> locationObservable = AppSingleton.getInstance().getEndPoint().getLocation(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(locationObservable);
    }

    /**
     * Find Location by Limit,Skip,Sort
     * @param options
     * @return
     */
    public static ExpObservable  findLocations(Map<String,String> options){
        Observable<ResultLocation> locationObservable = AppSingleton.getInstance().getEndPoint().findLocations(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(locationObservable);
    }

    /**
     * Get Content Node by UUID
     * @param uuid
     * @return
     */
    public static ExpObservable getContentNode(String uuid){
        Observable<ContentNode> contentNodeObservable = AppSingleton.getInstance().getEndPoint().getContentNode(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(contentNodeObservable);
    }

    /**
     * Get Data by group,key
     * @param group
     * @param key
     * @return
     */
    public static ExpObservable getData(String group,String key){
        Observable<Data> dataObservable = AppSingleton.getInstance().getEndPoint().getData(group, key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(dataObservable);
    }

    /**
     * Get Data by group,key
     * @param options
     * @return
     */
    public static ExpObservable findData(Map<String,String> options){
        Observable<ResultData> dataObservable = AppSingleton.getInstance().getEndPoint().findData(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(dataObservable);
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

    public static void connection(String name,Subscriber subscriber){
        socketManager.connection(name,subscriber);
    }
}

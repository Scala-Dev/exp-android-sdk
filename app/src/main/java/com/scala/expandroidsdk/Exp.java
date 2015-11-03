package com.scala.expandroidsdk;

import com.scala.expandroidsdk.adapters.ExpObservable;
import com.scala.expandroidsdk.model.ContentNode;
import com.scala.expandroidsdk.model.Device;
import com.scala.expandroidsdk.model.Experience;
import com.scala.expandroidsdk.model.Location;
import com.scala.expandroidsdk.model.ResultDevice;
import com.scala.expandroidsdk.model.ResultExperience;
import com.scala.expandroidsdk.model.ResultLocation;
import com.scala.expandroidsdk.model.ResultThing;
import com.scala.expandroidsdk.model.Thing;
import com.scala.expandroidsdk.model.Token;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Cesar Oyarzun on 10/28/15.
 */
public class Exp {

    private static Runtime runtime = new Runtime();

    /**
     *
     * @param host
     * @param uuid
     * @param secret
     * @return
     */
    public static ExpObservable start(String host,String uuid,String secret){
        return  runtime.start(host,uuid,secret);
    }

    /**
     * @param host
     * @param username
     * @param password
     * @return
     */
    public static Observable start(String host, String username, String password, String organization){
        return runtime.start(host,username,password,organization);
    }


    public static ExpObservable login(Map<String, String> options){
        Observable<Token> thingObservable = AppSingleton.getInstance().getEndPoint().login(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(thingObservable);
    }

    public static ExpObservable getThing(String uuid){
        Observable<Thing> thingObservable = AppSingleton.getInstance().getEndPoint().getThing(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(thingObservable);
    }

    public static ExpObservable  findthings(Map<String,String> options){
        Observable<ResultThing> resultThingObservable = AppSingleton.getInstance().getEndPoint().findThings(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(resultThingObservable);
    }

    public static ExpObservable getDevice(String uuid){
        Observable<Device> thingObservable = AppSingleton.getInstance().getEndPoint().getDevice(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(thingObservable);
    }

    public static ExpObservable  findDevices(Map<String,String> options){
        Observable<ResultDevice> resultThingObservable = AppSingleton.getInstance().getEndPoint().findDevices(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(resultThingObservable);
    }

    public static ExpObservable getExperience(String uuid){
        Observable<Experience> thingObservable = AppSingleton.getInstance().getEndPoint().getExperience(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(thingObservable);
    }

    public static ExpObservable  findExperiences(Map<String,String> options){
        Observable<ResultExperience> resultThingObservable = AppSingleton.getInstance().getEndPoint().findExperiences(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(resultThingObservable);
    }

    public static ExpObservable getLocation(String uuid){
        Observable<Location> thingObservable = AppSingleton.getInstance().getEndPoint().getLocation(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(thingObservable);
    }

    public static ExpObservable  findLocation(Map<String,String> options){
        Observable<ResultLocation> resultThingObservable = AppSingleton.getInstance().getEndPoint().findLocations(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(resultThingObservable);
    }

    public static ExpObservable getContentNode(String uuid){
        Observable<ContentNode> thingObservable = AppSingleton.getInstance().getEndPoint().getContentNode(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable(thingObservable);
    }

}

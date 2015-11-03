package com.scala.expandroidsdk;

import com.scala.expandroidsdk.observer.ExpObservable;
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

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Cesar Oyarzun on 10/28/15.
 */
public class Exp {

    public static final String LIMIT = "limit";
    public static final String SKIP = "skip";
    public static final String SORT = "sort";
    private static Runtime runtime = new Runtime();

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
     * @param limit
     * @param skip
     * @param sort
     * @return
     */
    public static ExpObservable  findthings(String limit,String skip,String sort){
        Map<String,String> options = new HashMap<>();
        options.put(LIMIT,limit);
        options.put(SKIP, skip);
        options.put(SORT, sort);
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
     * @param limit
     * @param skip
     * @param sort
     * @return
     */
    public static ExpObservable  findDevices(String limit,String skip,String sort){
        Map<String,String> options = new HashMap<>();
        options.put(LIMIT,limit);
        options.put(SKIP, skip);
        options.put(SORT, sort);
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
     * @param limit
     * @param skip
     * @param sort
     * @return
     */
    public static ExpObservable  findExperiences(String limit,String skip,String sort){
        Map<String,String> options = new HashMap<>();
        options.put(LIMIT,limit);
        options.put(SKIP, skip);
        options.put(SORT, sort);
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
     * @param limit
     * @param skip
     * @param sort
     * @return
     */
    public static ExpObservable  findLocation(String limit,String skip,String sort){
        Map<String,String> options = new HashMap<>();
        options.put(LIMIT,limit);
        options.put(SKIP, skip);
        options.put(SORT, sort);
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


}

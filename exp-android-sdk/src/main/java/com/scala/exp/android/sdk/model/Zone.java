package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar Oyarzun on 4/25/16.
 */
public class Zone  extends AbstractModel{

    private Location location;

    public String getKey() {
        return getString(Utils.KEY);
    }

    public String getName() {
        return getString(Utils.NAME);
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ExpObservable<SearchResults<Device>> getDevices(){
        Map options = new HashMap();
        Location location = getLocation();
        if(location!=null){
            options.put(Utils.LOCATION_UUID, getLocation().getString(Utils.UUID));
            options.put(Utils.LOCATION_ZONES_KEY, getKey());
        }
        return Exp.findDevices(options);
    }

    public ExpObservable<SearchResults<Thing>> getThings(){
        Map options = new HashMap();
        Location location = getLocation();
        if(location!=null){
            options.put(Utils.LOCATION_UUID, getLocation().getString(Utils.UUID));
            options.put(Utils.LOCATION_ZONES_KEY, getKey());
        }
        return Exp.findThings(options);
    }

    public ExpObservable<List<Zone>> getCurrentZones(){
        final ExpObservable<Device> observable = Device.getCurrentDevice();
            return new ExpObservable<List<Zone>>(observable.<List<Zone>>flatMap(new Func1<Device, Observable<List<Zone>>>() {
                @Override
                public Observable<List<Zone>> call(Device device) {
                    if(device!=null && device.getZones()!=null){
                        return Observable.just(device.getZones());
                    }else{
                        return Observable.just(null);
                    }
                }
            }));
    }

    @Override
    public ExpObservable<Location> save() {
       return this.location.save();
    }

    @Override
    public ExpObservable<Location> refresh() {
        return this.location.refresh();
    }

    @Override
    protected String getChannelName() {
        return this.location.getUuid()+":zone:"+getKey();
    }
}

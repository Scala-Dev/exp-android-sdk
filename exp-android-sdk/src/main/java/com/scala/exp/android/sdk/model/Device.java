package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class Device extends AbstractModel {

    private List<Zone> zones;
    private Location location;
    private Experience experience;

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public ExpObservable<Location> getLocation() {
        ExpObservable<Location> expObs= null;
        if(this.location!=null && this.location.getUuid()!=null){
            expObs = Exp.getLocation(this.location.getUuid());
        }else{
            expObs = new ExpObservable<Location>(Observable.<Location>empty());
        }
        return expObs;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public ExpObservable<Experience> getExperience() {
        ExpObservable<Experience> expObs= null;
        if(this.experience!=null && this.experience.getUuid()!=null){
            expObs = Exp.getExperience(this.experience.getUuid());
        }else{
            expObs = new ExpObservable<Experience>(Observable.<Experience>empty());
        }
        return expObs;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public static ExpObservable<Device> getCurrentDevice(){
        if(AppSingleton.getInstance().getAuth() != null){
            if(AppSingleton.getInstance().getAuth().getIdentity() == null){
                String uuid = AppSingleton.getInstance().getAuth().getIdentity().getUuid();
                return Exp.getDevice(uuid);
            }
        }
        return new ExpObservable<Device>(Observable.<Device>just(null));
    }


    @Override
    public ExpObservable<Device> save() {
        Observable<Device> deviceObservable = AppSingleton.getInstance().getEndPoint().saveDevice(getUuid(),getDocument())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Device>(deviceObservable);
    }

    @Override
    public ExpObservable<Device> refresh() {
        return Exp.getDevice(getUuid());
    }
}

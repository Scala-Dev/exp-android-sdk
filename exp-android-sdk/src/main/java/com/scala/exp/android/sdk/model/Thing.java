package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Cesar Oyarzun on 10/28/15.
 */
public class Thing extends AbstractModel {

    private List<Zone> zones;
    private Location location;
    private Experience experience;

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    @Override
    public ExpObservable<Thing> save() {
        Observable<Thing> thingObservable = AppSingleton.getInstance().getEndPoint().saveThing(getUuid(),getDocument())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Thing>(thingObservable);
    }

    @Override
    public ExpObservable<Thing> refresh() {
        return Exp.getThing(getUuid());
    }
}

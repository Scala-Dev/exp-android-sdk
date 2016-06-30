package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.List;

import rx.Observable;

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

    public static ExpObservable<Device> getCurrentDevice(){
        if(AppSingleton.getInstance().getAuth() != null){
            if(AppSingleton.getInstance().getAuth().getIdentity() != null){
                String uuid = AppSingleton.getInstance().getAuth().getIdentity().getUuid();
                return Exp.getDevice(uuid);
            }
        }
        return null;
    }
}

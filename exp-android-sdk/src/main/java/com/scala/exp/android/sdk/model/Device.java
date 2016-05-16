package com.scala.exp.android.sdk.model;

import java.util.List;

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
}

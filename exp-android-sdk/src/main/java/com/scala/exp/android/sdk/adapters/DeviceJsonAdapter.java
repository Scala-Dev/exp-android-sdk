package com.scala.exp.android.sdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.model.Device;
import com.scala.exp.android.sdk.model.Experience;
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.Zone;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class DeviceJsonAdapter implements JsonDeserializer<Device> {

    @Override
    public Device deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {

        LinkedTreeMap treeMap = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
        Device device = new Device();
        device.setProperties(treeMap);
        LinkedTreeMap locationDevice = (LinkedTreeMap) treeMap.get(Utils.LOCATION);
        if(locationDevice!=null){
            Location location = new Location();
            location.setProperties(locationDevice);
            device.setLocation(location);
            List<LinkedTreeMap> zonesList = (List<LinkedTreeMap>) locationDevice.get(Utils.ZONES);
            List<Zone> zonesArray = new ArrayList<Zone>();
            if(zonesList != null && !zonesList.isEmpty()){
                for (LinkedTreeMap child : zonesList) {
                    Zone zone = new Zone();
                    zone.setProperties(child);
                    zone.setLocation(location);
                    zonesArray.add(zone);
                }
                device.setZones(zonesArray);
            }
        }
        LinkedTreeMap experienceDevice = (LinkedTreeMap) treeMap.get(Utils.EXPERIENCE);
        if(experienceDevice!=null){
            Experience experience=new Experience();
            experience.setProperties(experienceDevice);
            device.setExperience(experience);
        }
        return device;
    }
}
package com.scala.exp.android.sdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.Zone;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class LocationJsonAdapter implements JsonDeserializer<Location>,IExpDeserializer {

    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {
        return expDeserialzier(json);
    }

    @Override
    public Location expDeserialzier(JsonElement json) {
        return deserialize(json);
    }


    public static Location deserialize(JsonElement json){
        LinkedTreeMap treeMap = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
        Location location = new Location();
        location.setProperties(treeMap);
        List<LinkedTreeMap> zonesList = (List<LinkedTreeMap>) treeMap.get(Utils.ZONES);
        if(zonesList != null && !zonesList.isEmpty()){
            List<Zone> zonesArray = new ArrayList<Zone>();
            for (LinkedTreeMap child : zonesList) {
                Zone zone = new Zone();
                zone.setProperties(child);
                zone.setLocation(location);
                zonesArray.add(zone);
            }
            location.setZones(zonesArray);
        }
        return location;
    }
}
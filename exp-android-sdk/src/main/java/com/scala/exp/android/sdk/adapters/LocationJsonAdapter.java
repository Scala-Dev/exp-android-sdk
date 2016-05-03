package com.scala.exp.android.sdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.model.Content;
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.Zone;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class LocationJsonAdapter implements JsonDeserializer<Location> {

    public static final String KEY = "key";
    public static final String NAME = "name";
    public static final String ZONES = "zones";

    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {

        LinkedTreeMap treeMap = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
        Location location = new Location();
        location.setProperties(treeMap);
        List<LinkedTreeMap> zonesList = (List<LinkedTreeMap>) treeMap.get(ZONES);
        List<Zone> zonesArray = new ArrayList<Zone>();
        if(zonesList != null && !zonesList.isEmpty()){
            for (LinkedTreeMap child : zonesList) {
                String key = (String) child.get(KEY);
                String name = (String) child.get(NAME);
                Zone zone = new Zone();
                zone.setKey(key);
                zone.setName(name);
                zonesArray.add(zone);
            }
            location.setZones(zonesArray);
        }
        return location;
    }
}
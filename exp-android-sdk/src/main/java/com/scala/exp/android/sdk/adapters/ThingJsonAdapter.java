package com.scala.exp.android.sdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.model.Experience;
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.Thing;
import com.scala.exp.android.sdk.model.Zone;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class ThingJsonAdapter implements JsonDeserializer<Thing>,IExpDeserializer {

    @Override
    public Thing deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {
        LinkedTreeMap treeMap = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
        return expDeserialzier(treeMap);
    }

    @Override
    public Thing expDeserialzier(Map map) {
        return deserialize(map);
    }

    public static Thing deserialize(Map treeMap){
        Thing thing = new Thing();
        thing.setProperties((LinkedTreeMap) treeMap);
        LinkedTreeMap locationThing = (LinkedTreeMap) treeMap.get(Utils.LOCATION);
        if(locationThing!=null){
            Location location = new Location();
            location.setProperties(locationThing);
            thing.setLocation(location);
            List<LinkedTreeMap> zonesList = (List<LinkedTreeMap>) locationThing.get(Utils.ZONES);
            List<Zone> zonesArray = new ArrayList<Zone>();
            if(zonesList != null && !zonesList.isEmpty()){
                for (LinkedTreeMap child : zonesList) {
                    Zone zone = new Zone();
                    zone.setProperties(child);
                    zone.setLocation(location);
                    zonesArray.add(zone);
                }
                thing.setZones(zonesArray);
            }
        }
        LinkedTreeMap experienceDevice = (LinkedTreeMap) treeMap.get(Utils.EXPERIENCE);
        if(experienceDevice!=null){
            Experience experience=new Experience();
            experience.setProperties(experienceDevice);
            thing.setExperience(experience);
        }
        return thing;
    }


}
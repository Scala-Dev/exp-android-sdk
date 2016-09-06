package com.scala.exp.android.sdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.model.AbstractModel;
import com.scala.exp.android.sdk.model.Content;
import com.scala.exp.android.sdk.model.Device;
import com.scala.exp.android.sdk.model.Experience;
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.SearchResults;
import com.scala.exp.android.sdk.model.Thing;
import com.scala.exp.android.sdk.model.Zone;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class SearchModelJsonAdapter<T extends AbstractModel> implements JsonDeserializer<SearchResults<T>> {

    public static final String CONTENT = "com.scala.exp.android.sdk.model.Content";
    public static final String THING = "com.scala.exp.android.sdk.model.Thing";
    public static final String LOCATION = "com.scala.exp.android.sdk.model.Location";
    public static final String DEVICE = "com.scala.exp.android.sdk.model.Device";
    private Class<T> type;


    public SearchModelJsonAdapter(Class<T> cls) {
        this.type = cls;
    }


    @Override
    public SearchResults<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {
        SearchResults<T> searchResults = new SearchResults<>();
        LinkedTreeMap treeMap = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
        List<LinkedTreeMap> results = (List<LinkedTreeMap>) treeMap.get(Utils.RESULTS);
        Double total = (Double) treeMap.get(Utils.TOTAL);
        createModel(results,searchResults,treeMap);
        searchResults.setProperties(treeMap);
        searchResults.setTotal(total.intValue());
        return searchResults;
    }


    private void createModel(List<LinkedTreeMap> results, SearchResults<T> searchResults, LinkedTreeMap treeMap) {
        if (results != null && !results.isEmpty()) {
            switch (type.getName()) {
                case CONTENT:
                    List<Content> contentArray = new ArrayList<Content>();
                    for (LinkedTreeMap child : results) {
                        String subtype = (String) child.get(Utils.SUBTYPE);
                        Content content = new Content(Utils.getContentTypeEnum(subtype));
                        content.setProperties(treeMap);
                        contentArray.add(content);
                    }
                    searchResults.setResults((List<T>) contentArray);
                    break;
                case THING:
                    List<Thing> thingArray = new ArrayList<Thing>();
                    for (LinkedTreeMap child : results) {
                        Thing thing = new Thing();
                        thing.setProperties(treeMap);
                        LinkedTreeMap locationThing = (LinkedTreeMap) treeMap.get(Utils.LOCATION);
                        if (locationThing != null) {
                            Location location = new Location();
                            location.setProperties(locationThing);
                            thing.setLocation(location);
                            List<LinkedTreeMap> zonesList = (List<LinkedTreeMap>) locationThing.get(Utils.ZONES);
                            List<Zone> zonesArray = new ArrayList<Zone>();
                            if (zonesList != null && !zonesList.isEmpty()) {
                                for (LinkedTreeMap childZone : zonesList) {
                                    Zone zone = new Zone();
                                    zone.setProperties(childZone);
                                    zone.setLocation(location);
                                    zonesArray.add(zone);
                                }
                                thing.setZones(zonesArray);
                            }
                        }
                        LinkedTreeMap experienceDevice = (LinkedTreeMap) treeMap.get(Utils.EXPERIENCE);
                        if (experienceDevice != null) {
                            Experience experience = new Experience();
                            experience.setProperties(experienceDevice);
                            thing.setExperience(experience);
                        }
                        thingArray.add(thing);
                    }
                    searchResults.setResults((List<T>) thingArray);
                    break;
                case LOCATION:
                    List<Location> locationArray = new ArrayList<Location>();
                    for (LinkedTreeMap child : results) {
                        Location location = new Location();
                        location.setProperties(treeMap);
                        List<LinkedTreeMap> zonesList = (List<LinkedTreeMap>) treeMap.get(Utils.ZONES);
                        if (zonesList != null && !zonesList.isEmpty()) {
                            List<Zone> zonesArray = new ArrayList<Zone>();
                            for (LinkedTreeMap childZone : zonesList) {
                                Zone zone = new Zone();
                                zone.setProperties(childZone);
                                zone.setLocation(location);
                                zonesArray.add(zone);
                            }
                            location.setZones(zonesArray);
                        }
                        locationArray.add(location);
                    }
                    searchResults.setResults((List<T>) locationArray);
                    break;
                case DEVICE:
                    List<Device> deviceArray = new ArrayList<Device>();
                    for (LinkedTreeMap child : results) {
                        Device device = new Device();
                        device.setProperties(treeMap);
                        LinkedTreeMap locationDevice = (LinkedTreeMap) treeMap.get(Utils.LOCATION);
                        if (locationDevice != null) {
                            Location location = new Location();
                            location.setProperties(locationDevice);
                            device.setLocation(location);
                            List<LinkedTreeMap> zonesList = (List<LinkedTreeMap>) locationDevice.get(Utils.ZONES);
                            List<Zone> zonesArray = new ArrayList<Zone>();
                            if (zonesList != null && !zonesList.isEmpty()) {
                                for (LinkedTreeMap childZone : zonesList) {
                                    Zone zone = new Zone();
                                    zone.setProperties(childZone);
                                    zone.setLocation(location);
                                    zonesArray.add(zone);
                                }
                                device.setZones(zonesArray);
                            }
                        }
                        LinkedTreeMap experienceDevice = (LinkedTreeMap) treeMap.get(Utils.EXPERIENCE);
                        if (experienceDevice != null) {
                            Experience experience = new Experience();
                            experience.setProperties(experienceDevice);
                            device.setExperience(experience);
                        }
                        deviceArray.add(device);
                    }
                    searchResults.setResults((List<T>) deviceArray);
                    break;
                default:
                    List<T> list = new ArrayList<T>();
                    for (LinkedTreeMap child : results) {
                        try {
                            T data = type.newInstance();
                            data.setProperties(treeMap);
                            list.add(data);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                    searchResults.setResults(list);
            }
        }
    }
}
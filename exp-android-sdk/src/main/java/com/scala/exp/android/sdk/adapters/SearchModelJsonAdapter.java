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
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.SearchResults;
import com.scala.exp.android.sdk.model.Thing;

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
        createModel(results, searchResults, json);
        searchResults.setProperties(treeMap);
        searchResults.setTotal(total.intValue());
        return searchResults;
    }


    private void createModel(List<LinkedTreeMap> results, SearchResults<T> searchResults, JsonElement json) {
        if (results != null && !results.isEmpty()) {
            switch (type.getName()) {
                case CONTENT:
                    List<Content> contentArray = new ArrayList<Content>();
                    for (LinkedTreeMap child : results) {
                        contentArray.add(ContentJsonAdapter.deserialize(child));
                    }
                    searchResults.setResults((List<T>) contentArray);
                    break;
                case THING:
                    List<Thing> thingArray = new ArrayList<Thing>();
                    for (LinkedTreeMap child : results) {
                        thingArray.add(ThingJsonAdapter.deserialize(child));
                    }
                    searchResults.setResults((List<T>) thingArray);
                    break;
                case LOCATION:
                    List<Location> locationArray = new ArrayList<Location>();
                    for (LinkedTreeMap child : results) {
                        locationArray.add(LocationJsonAdapter.deserialize(child));
                    }
                    searchResults.setResults((List<T>) locationArray);
                    break;
                case DEVICE:
                    List<Device> deviceArray = new ArrayList<Device>();
                    for (LinkedTreeMap child : results) {
                        deviceArray.add(DeviceJsonAdapter.deserialize(child));
                    }
                    searchResults.setResults((List<T>) deviceArray);
                    break;

                default:
                    List<T> list = new ArrayList<T>();
                    for (LinkedTreeMap child : results) {
                        ModelJsonAdapter modelJsonAdapter = new ModelJsonAdapter(type);
                        list.add((T) modelJsonAdapter.expDeserialzier(child));
                    }
                    searchResults.setResults(list);
                    break;
            }
        }
    }
}
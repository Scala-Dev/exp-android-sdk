package com.sdk.expandroidsdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.sdk.expandroidsdk.AppSingleton;
import com.sdk.expandroidsdk.model.Data;

import java.lang.reflect.Type;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class DataJsonAdapter implements JsonDeserializer<Data> {
    @Override
    public Data deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {
        Data data = new Data();
        LinkedTreeMap treeMap = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
        data.setProperties(treeMap);
        return data;
    }
}
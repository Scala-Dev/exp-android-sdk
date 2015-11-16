package com.sdk.expandroidsdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.sdk.expandroidsdk.AppSingleton;
import com.sdk.expandroidsdk.model.Thing;

import java.lang.reflect.Type;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class ThingJsonAdapter implements JsonDeserializer<Thing> {


    @Override
    public Thing deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {
        Thing thing = new Thing();
        LinkedTreeMap treeMap = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
        thing.setProperties(treeMap);
        return thing;
    }
}
package com.scala.exp.android.sdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.model.Thing;

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
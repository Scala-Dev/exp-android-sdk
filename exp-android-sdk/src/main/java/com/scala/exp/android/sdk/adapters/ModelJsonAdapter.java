package com.scala.exp.android.sdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.model.AbstractModel;
import com.scala.exp.android.sdk.model.IExpModel;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class ModelJsonAdapter<T extends AbstractModel> implements JsonDeserializer<T>,IExpDeserializer {

    private Class<T> type;

    public ModelJsonAdapter(Class<T> cls) {
        this.type = cls;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {
        LinkedTreeMap treeMap = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
        return (T) expDeserialzier(treeMap);

    }

    @Override
    public IExpModel expDeserialzier(Map treeMap) {
        try {
            T data = type.newInstance();
            data.setProperties((LinkedTreeMap) treeMap);
            return data;

        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
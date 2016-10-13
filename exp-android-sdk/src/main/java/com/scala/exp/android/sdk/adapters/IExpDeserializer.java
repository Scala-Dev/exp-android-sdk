package com.scala.exp.android.sdk.adapters;

import com.google.gson.JsonElement;
import com.scala.exp.android.sdk.model.IExpModel;

/**
 * Created by Cesar Oyarzun on 9/6/16.
 */
public interface IExpDeserializer {
    public IExpModel expDeserialzier(JsonElement json);
}

package com.scala.exp.android.sdk.adapters;

import com.scala.exp.android.sdk.model.IExpModel;

import java.util.Map;

/**
 * Created by Cesar Oyarzun on 9/6/16.
 */
public interface IExpDeserializer {
    public IExpModel expDeserialzier(Map treeMap);
}

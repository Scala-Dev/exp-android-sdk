package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.Map;

/**
 * Created by Cesar Oyarzun on 9/6/16.
 */
public interface IExpModel {
    ExpObservable<?> save();
    void setProperty(String name,Object value);
}

package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class Experience extends AbstractModel {


    public ExpObservable<SearchResults<Device>> getDevices(){
        Map options = new HashMap();
        options.put(Utils.LOCATION_UUID,getUuid());
        return Exp.findDevices(options);
    }
}

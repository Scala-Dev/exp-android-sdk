package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.AppSingleton;

/**
 * Created by Cesar Oyarzun on 10/28/15.
 */

public class Location extends AbstractModel {

    public String getLayoutUrl(){
        return  AppSingleton.getInstance().getHost()+"/api/locations/"+getString("uuid")+"/layout";
    }

}
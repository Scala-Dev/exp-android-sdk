package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar Oyarzun on 10/28/15.
 */

public class Location extends AbstractModel {

    private static final String API_LOCATION = "/api/locations/";
    private static final String LAYOUT = "/layout";
    private static final String RT = "_rt=";
    private List<Zone> zones;

    public String getLayoutUrl(){
        String host = AppSingleton.getInstance().getHost();
        String rt = AppSingleton.getInstance().getAuth().getRestrictedToken();

        StringBuilder builder = new StringBuilder(host)
                .append(API_LOCATION)
                .append(getString(Utils.UUID))
                .append(LAYOUT)
                .append("?").append(RT).append(rt);

        return  builder.toString();
    }


    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public List<Zone> getZones() {
        return zones;
    }
}
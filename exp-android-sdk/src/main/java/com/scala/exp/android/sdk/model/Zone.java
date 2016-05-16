package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.Utils;

/**
 * Created by Cesar Oyarzun on 4/25/16.
 */
public class Zone  extends AbstractModel{

    public String getKey() {
        return getString(Utils.KEY);
    }

    public String getName() {
        return getString(Utils.NAME);
    }
}

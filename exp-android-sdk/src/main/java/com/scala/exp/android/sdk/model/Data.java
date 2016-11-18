package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.channels.IChannel;
import com.scala.exp.android.sdk.observer.ExpObservable;

/**
 * Created by Cesar Oyarzun on 10/28/15.
 */
public class Data extends AbstractModel {

    @Override
    protected String getChannelName() {
        String key = getString(Utils.KEY);
        String group = getString(Utils.GROUP);
        return "data:" + key + group;
    }

    @Override
    public ExpObservable<Data> refresh() {
        return Exp.getData(getString(Utils.GROUP),getString(Utils.KEY));
    }
}

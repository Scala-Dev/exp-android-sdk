package com.scala.exp.android.sdk.model;

import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.channels.IChannel;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Cesar Oyarzun on 10/28/15.
 */
public class Data extends AbstractModel {

    public String getGroup(){
        return getString(Utils.GROUP);
    }

    public String getKey(){
        return getString(Utils.KEY);
    }

    public Map getValue(){
        return (LinkedTreeMap) get(Utils.VALUE);
    }

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

    @Override
    public ExpObservable<Data> save() {
        Observable<Data> dataObservable = AppSingleton.getInstance().getEndPoint().createData(getString(Utils.GROUP),getString(Utils.KEY),getDocument())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Data>(dataObservable);
    }
}

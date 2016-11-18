package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class Feed extends AbstractModel {

    public ExpObservable<Map> getData() {
        final String uuid = getString(Utils.UUID);
        Observable<Map> observable = AppSingleton.getInstance().getEndPoint().getFeedData(uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Map>(observable);
    }


    public ExpObservable<Map> getData(Map<String,Object> options) {
        final String uuid = getString(Utils.UUID);
        Observable<Map> observable = AppSingleton.getInstance().getEndPoint().getFeedData(uuid, options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        return new ExpObservable<Map>(observable);
    }

    @Override
    public ExpObservable<Feed> save() {
        Observable<Feed> observable = AppSingleton.getInstance().getEndPoint().saveFeed(getUuid(),getDocument())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Feed>(observable);
    }

    @Override
    public ExpObservable<Feed> refresh() {
        return Exp.getFeed(getUuid());
    }
}

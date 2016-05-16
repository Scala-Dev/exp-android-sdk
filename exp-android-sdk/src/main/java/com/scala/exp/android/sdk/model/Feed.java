package com.scala.exp.android.sdk.model;

import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class Feed extends AbstractModel {

    public ExpObservable<Map> getData() {
        final String uuid = getString(Utils.UUID);
        Observable<Map> observable = AppSingleton.getInstance().getEndPoint().getFeedData(Utils.UUID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Map>(observable);
    }

}

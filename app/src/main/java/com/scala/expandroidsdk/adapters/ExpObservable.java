package com.scala.expandroidsdk.adapters;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by Cesar Oyarzun on 10/29/15.
 */
public class ExpObservable {
    Observable observable = null;

    public ExpObservable(Observable<?> observable){
        this.observable = observable;
    }

    public Observable map(Func1 func){
        return this.observable.map(func);
    }

    public Subscription then(Subscriber<?> subscriber){
        return this.observable.subscribe(subscriber);
    }
}

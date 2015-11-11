package com.scala.expandroidsdk.observer;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * ExpObservable Wrapper for rx.Observable
 * Created by Cesar Oyarzun on 10/29/15.
 */
public class ExpObservable {
    Observable observable = null;

    public ExpObservable(Observable<?> observable){
        this.observable = observable;
    }

    public Observable flatMap(Func1 func){
        return this.observable.flatMap(func);
    }

    public Observable map(Func1 func){
        return this.observable.map(func);
    }

    public Subscription then(Subscriber<?> subscriber){
        return this.observable.subscribe(subscriber);
    }

    public final Subscription then(final Action1 onNext) {
        return this.observable.subscribe(onNext);
    }
}

package com.scala.exp.android.sdk.observer;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * ExpObservable Wrapper for rx.Observable
 * Created by Cesar Oyarzun on 10/29/15.
 */
public class ExpObservable<T> {

    private Observable observable = null;

    public ExpObservable(Observable<T> observable){
        this.observable = observable;
    }

    public <R> Observable<R> flatMap(Func1<?, Observable<R>> func){
        return this.observable.flatMap(func);
    }

    public <R> Observable<R> flatMapExp(Func1<?, ExpObservable<R>> func){
        return this.observable.flatMap(func);
    }

    public <R> Observable<R> map(Func1<?, Observable<R>> func){
        return this.observable.map(func);
    }

    public Subscription then(Subscriber<?> subscriber){
        return this.observable.subscribe(subscriber);
    }

    public final Subscription then(final Action1 onNext) {
        return this.observable.subscribe(onNext);
    }

    public Observable<T> getObservable() { return this.observable; }
}

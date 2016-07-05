package com.scala.exp.android.sdk.model;

import android.util.Log;

import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class Experience extends AbstractModel {


    public ExpObservable<SearchResults<Device>> getDevices(){
        Map options = new HashMap();
        options.put(Utils.LOCATION_UUID,getUuid());
        return Exp.findDevices(options);
    }

    public ExpObservable<Experience> getCurrentExperience(){
        final ExpObservable<?> observable = Device.getCurrentDevice();
            return new ExpObservable<Experience>(observable.<Experience>flatMap(new Func1<Device, Observable<Experience>>() {
                @Override
                public Observable<Experience> call(Device device) {
                    if(device!=null && device.getExperience()!=null){
                        return Observable.just(device.getExperience());
                    }else{
                        return Observable.just(null);
                    }
                }
            }));

    }
}

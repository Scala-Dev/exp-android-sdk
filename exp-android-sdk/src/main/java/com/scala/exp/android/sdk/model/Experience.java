package com.scala.exp.android.sdk.model;

import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.observer.ExpObservable;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class Experience extends AbstractModel {


    public static final String EXPERIENCE_UUID = "experience.uuid";

    public ExpObservable<SearchResults<Device>> getDevices(){
        Map options = new HashMap();
        options.put(Utils.LOCATION_UUID,getUuid());
        return Exp.findDevices(options);
    }

    public ExpObservable<Experience> getCurrentExperience(){
        final ExpObservable<Device> observable = Device.getCurrentDevice();
            return new ExpObservable<Experience>(observable.<Experience>flatMapExp(new Func1<Device, ExpObservable<Experience>>() {
                @Override
                public ExpObservable<Experience> call(Device device) {
                    if (device != null && device.get(EXPERIENCE_UUID) != null) {
                        return device.getExperience();
                    } else {
                        return new ExpObservable<Experience>(Observable.<Experience>empty());
                    }
                }
            }));
    }

    @Override
    public ExpObservable<Experience> save() {
        Observable<Experience> experienceObservable = AppSingleton.getInstance().getEndPoint().saveExperience(getUuid(),getDocument())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return new ExpObservable<Experience>(experienceObservable);
    }

    @Override
    public ExpObservable<Experience> refresh() {
        return Exp.getExperience(getUuid());
    }
}

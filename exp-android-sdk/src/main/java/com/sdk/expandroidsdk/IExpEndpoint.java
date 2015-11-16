package com.sdk.expandroidsdk;

import com.sdk.expandroidsdk.model.ContentNode;
import com.sdk.expandroidsdk.model.Data;
import com.sdk.expandroidsdk.model.Device;
import com.sdk.expandroidsdk.model.Experience;
import com.sdk.expandroidsdk.model.Location;
import com.sdk.expandroidsdk.model.ResultData;
import com.sdk.expandroidsdk.model.ResultDevice;
import com.sdk.expandroidsdk.model.ResultExperience;
import com.sdk.expandroidsdk.model.ResultLocation;
import com.sdk.expandroidsdk.model.ResultThing;
import com.sdk.expandroidsdk.model.Thing;
import com.sdk.expandroidsdk.model.Token;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * End Point EXP service
 * Created by Cesar Oyarzun on 10/27/15.
 */
public interface IExpEndpoint {

        @POST("/api/auth/login")
        Observable<Token> login(@Body Map<String,String> options);

        @GET("/api/things/{uuid}")
        Observable<Thing> getThing(@Path("uuid") String uuid);

        @GET("/api/things")
        Observable<ResultThing> findThings(@QueryMap Map<String,String> options);

        @GET("/api/devices/{uuid}")
        Observable<Device> getDevice(@Path("uuid") String uuid);

        @GET("/api/devices")
        Observable<ResultDevice> findDevices(@QueryMap Map<String,String> options);

        @GET("/api/experiences/{uuid}")
        Observable<Experience> getExperience(@Path("uuid") String uuid);

        @GET("/api/experiences")
        Observable<ResultExperience> findExperiences(@QueryMap Map<String,String> options);

        @GET("/api/locations/{uuid}")
        Observable<Location> getLocation(@Path("uuid") String uuid);

        @GET("/api/locations")
        Observable<ResultLocation> findLocations(@QueryMap Map<String,String> options);

        @GET("/api/content/{uuid}/children")
        Observable<ContentNode> getContentNode(@Path("uuid") String uuid);

        @GET("/api/data/{group}/{key}")
        Observable<Data> getData(@Path("group") String group,@Path("key") String key);

        @GET("/api/data/{group}/{key}")
        Observable<ResultData> findData(@QueryMap Map<String,String> options);

}

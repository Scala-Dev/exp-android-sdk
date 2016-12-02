package com.scala.exp.android.sdk;

import com.scala.exp.android.sdk.model.Auth;
import com.scala.exp.android.sdk.model.Content;
import com.scala.exp.android.sdk.model.ContentNode;
import com.scala.exp.android.sdk.model.Data;
import com.scala.exp.android.sdk.model.Device;
import com.scala.exp.android.sdk.model.Experience;
import com.scala.exp.android.sdk.model.Feed;
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.Message;
import com.scala.exp.android.sdk.model.SearchResults;
import com.scala.exp.android.sdk.model.Thing;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * End Point EXP service
 * Created by Cesar Oyarzun on 10/27/15.
 */
public interface IExpEndpoint {

        @POST("/api/auth/login")
        Observable<Auth> login(@Body Map<String, Object> options);

        @GET("/api/things/{uuid}")
        Observable<Thing> getThing(@Path("uuid") String uuid);

        @GET("/api/things")
        Observable<SearchResults<Thing>> findThings(@QueryMap Map<String, Object> options);

        @PATCH("/api/things/{uuid}")
        Observable<Thing> saveThing(@Path("uuid") String uuid, @Body Map<String, Object> document);

        @POST("/api/things")
        Observable<Thing> createThing(@Body Map<String, Object> document);

        @DELETE("/api/things/{uuid}")
        Observable<Void> deleteThing(@Path("uuid") String uuid);

        @GET("/api/devices/{uuid}")
        Observable<Device> getDevice(@Path("uuid") String uuid);

        @GET("/api/devices")
        Observable<SearchResults<Device>> findDevices(@QueryMap Map<String, Object> options);

        @POST("/api/devices")
        Observable<Device> createDevice(@Body Map<String, Object> document);

        @PATCH("/api/devices/{uuid}")
        Observable<Device> saveDevice(@Path("uuid") String uuid, @Body Map<String, Object> document);

        @GET("/api/experiences/{uuid}")
        Observable<Experience> getExperience(@Path("uuid") String uuid);

        @GET("/api/experiences")
        Observable<SearchResults<Experience>> findExperiences(@QueryMap Map<String, Object> options);

        @PATCH("/api/experiences/{uuid}")
        Observable<Experience> saveExperience(@Path("uuid") String uuid, @Body Map<String, Object> document);

        @POST("/api/experiences")
        Observable<Experience> createExperience(@Body Map<String, Object> document);

        @DELETE("/api/experiences/{uuid}")
        Observable<Void> deleteExperience(@Path("uuid") String uuid);

        @GET("/api/locations/{uuid}")
        Observable<Location> getLocation(@Path("uuid") String uuid);

        @GET("/api/locations")
        Observable<SearchResults<Location>> findLocations(@QueryMap Map<String, Object> options);

        @PATCH("/api/locations/{uuid}")
        Observable<Location> saveLocation(@Path("uuid") String uuid,@Body Map<String, Object> document);

        @DELETE("/api/locations/{uuid}")
        Observable<Void> deleteLocation(@Path("uuid") String uuid);

        @POST("/api/locations")
        Observable<Location> createLocation(@Body Map<String, Object> document);

        @GET("/api/content/{uuid}")
        Observable<Content> getContent(@Path("uuid") String uuid);

        @GET("/api/content/{uuid}/children")
        Observable<ContentNode> getContentNode(@Path("uuid") String uuid);

        @GET("/api/content")
        Observable<SearchResults<Content>> findContent(@QueryMap Map<String, Object> options);

        @PATCH("/api/content/{uuid}")
        Observable<Content> saveContent(@Path("uuid") String uuid, @Body Map<String, Object> document);

        @GET("/api/content")
        Observable<SearchResults<ContentNode>> findContentNodes(@QueryMap Map<String, Object> options);

        @GET("/api/data/{group}/{key}")
        Observable<Data> getData(@Path("group") String group, @Path("key") String key);

        @GET("/api/data")
        Observable<SearchResults<Data>> findData(@QueryMap Map<String, Object> options);

        @PUT("/api/data/{group}/{key}")
        Observable<Data> createData(@Path("group") String group, @Path("key") String key,@Body Map<String, Object> document);

        @DELETE("/api/data/{group}/{key}")
        Observable<Void> deleteData(@Path("group") String group, @Path("key") String key);

        @GET("/api/connectors/feeds/{uuid}")
        Observable<Feed> getFeed(@Path("uuid") String uuid);

        @GET("/api/connectors/feeds")
        Observable<SearchResults<Feed>> findFeeds(@QueryMap Map<String, Object> options);

        @PATCH("/api/connectors/feeds/{uuid}")
        Observable<Feed> saveFeed(@Path("uuid") String uuid,@Body Map<String, Object> document);

        @DELETE("/api/connectors/feeds/{uuid}")
        Observable<Void> deleteFeed(@Path("uuid") String uuid);

        @GET("/api/connectors/feeds/{uuid}/data")
        Observable<Map> getFeedData(@Path("uuid") String uuid);

        @GET("/api/connectors/feeds/{uuid}/data")
        Observable<Map> getFeedData(@Path("uuid") String uuid, @QueryMap Map<String, Object> options);

        @POST("/api/connectors/feeds")
        Observable<Feed> createFeed(@Body Map<String, Object> document);

        @POST("/api/auth/token")
        Observable<Auth> refreshToken();

        @POST("/api/networks/current/broadcasts")
        Observable<Message> broadCast(@Body Map<String, Object> options, @Query("timeout") int timeout);

        @POST("/api/networks/current/responses")
        Observable<Message> respond(@Body Map<String, Object> options);

        @DELETE("/api/devices/{uuid}")
        Observable<Void> deleteDevice(@Path("uuid") String uuid);


}

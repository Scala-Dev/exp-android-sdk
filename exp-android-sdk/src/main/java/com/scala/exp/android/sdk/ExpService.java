package com.scala.exp.android.sdk;

/**
 * Created by Cesar Oyarzun on 10/27/15.
 */


import com.google.gson.GsonBuilder;
import com.scala.exp.android.sdk.adapters.ContentNodeJsonAdapter;
import com.scala.exp.android.sdk.adapters.DataJsonAdapter;
import com.scala.exp.android.sdk.adapters.DeviceJsonAdapter;
import com.scala.exp.android.sdk.adapters.ExperienceJsonAdapter;
import com.scala.exp.android.sdk.adapters.LocationJsonAdapter;
import com.scala.exp.android.sdk.adapters.ThingJsonAdapter;
import com.scala.exp.android.sdk.model.ContentNode;
import com.scala.exp.android.sdk.model.Data;
import com.scala.exp.android.sdk.model.Device;
import com.scala.exp.android.sdk.model.Experience;
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.Thing;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;


public final class ExpService {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";


    /**
     * Init connection to Rest client TODO make this ExpObservable
     * @param host
     * @param tokenExp
     * @return
     */
    public static Observable init(String host, final String tokenExp)  {

        AppSingleton.getInstance().setHost(host);
        // Define the interceptor, add authentication headers
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader(AUTHORIZATION, BEARER +tokenExp).build();
                return chain.proceed(newRequest);
            }
        };

        // Add the interceptor to OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);


        //GSON builder adapter for model
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Device.class, new DeviceJsonAdapter());
        gson.registerTypeAdapter(Thing.class, new ThingJsonAdapter());
        gson.registerTypeAdapter(Location.class, new LocationJsonAdapter());
        gson.registerTypeAdapter(ContentNode.class, new ContentNodeJsonAdapter());
        gson.registerTypeAdapter(Data.class, new DataJsonAdapter());
        gson.registerTypeAdapter(Experience.class, new ExperienceJsonAdapter());
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson.create());

        // Create  REST adapter  API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .build();

        // Create an instance of our RestCall API interface.
        IExpEndpoint expApi = retrofit.create(IExpEndpoint.class);
        AppSingleton.getInstance().setEndPoint(expApi);

        return Observable.just(true);
    }

    /**
     * Init rest endpoint without interceptor TODO make this ExpObservable
     * @param host
     * @return
     */
    public static Observable init(String host)  {

        AppSingleton.getInstance().setHost(host);
        //GSON builder adapter for model
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Device.class, new DeviceJsonAdapter());
        gson.registerTypeAdapter(Thing.class, new ThingJsonAdapter());
        gson.registerTypeAdapter(Location.class, new LocationJsonAdapter());
        gson.registerTypeAdapter(ContentNode.class, new ContentNodeJsonAdapter());
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson.create());

        // Create  REST adapter  API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();
        // Create an instance of our RestCall API interface.
        IExpEndpoint expApi = retrofit.create(IExpEndpoint.class);
        AppSingleton.getInstance().setEndPoint(expApi);
        return Observable.just(true);
    }



}


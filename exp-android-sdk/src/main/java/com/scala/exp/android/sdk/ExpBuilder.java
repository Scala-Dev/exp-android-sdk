package com.scala.exp.android.sdk;

import com.google.gson.GsonBuilder;
import com.scala.exp.android.sdk.adapters.ContentJsonAdapter;
import com.scala.exp.android.sdk.adapters.ContentNodeJsonAdapter;
import com.scala.exp.android.sdk.adapters.DeviceJsonAdapter;
import com.scala.exp.android.sdk.adapters.LocationJsonAdapter;
import com.scala.exp.android.sdk.adapters.ModelJsonAdapter;
import com.scala.exp.android.sdk.adapters.SearchModelJsonAdapter;
import com.scala.exp.android.sdk.adapters.ThingJsonAdapter;
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

/**
 * Created by Cesar Oyarzun on 9/2/16.
 */
public class ExpBuilder {

    private GsonBuilder gsonBuilder;
    private static ExpBuilder instance = null;
    public static ExpBuilder getInstance() {
        if(instance == null) {
            instance = new ExpBuilder();
        }
        return instance;
    }

    public ExpBuilder(){
        //GSON builder adapter for model
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Device.class, new DeviceJsonAdapter());
        gsonBuilder.registerTypeAdapter(Thing.class, new ThingJsonAdapter());
        gsonBuilder.registerTypeAdapter(Location.class, new LocationJsonAdapter());
        gsonBuilder.registerTypeAdapter(Experience.class, new ModelJsonAdapter<Experience>(Experience.class));
        gsonBuilder.registerTypeAdapter(Data.class, new ModelJsonAdapter<Data>(Data.class));
        gsonBuilder.registerTypeAdapter(Feed.class, new ModelJsonAdapter<Feed>(Feed.class));
        gsonBuilder.registerTypeAdapter(Message.class, new ModelJsonAdapter<Message>(Message.class));
        gsonBuilder.registerTypeAdapter(Content.class, new ContentJsonAdapter());
        gsonBuilder.registerTypeAdapter(ContentNode.class, new ContentNodeJsonAdapter());
        gsonBuilder.registerTypeAdapter(SearchResults.class,new SearchModelJsonAdapter<Content>(Content.class));
        gsonBuilder.registerTypeAdapter(SearchResults.class,new SearchModelJsonAdapter<Device>(Device.class));
        gsonBuilder.registerTypeAdapter(SearchResults.class,new SearchModelJsonAdapter<Thing>(Thing.class));
        gsonBuilder.registerTypeAdapter(SearchResults.class,new SearchModelJsonAdapter<Location>(Location.class));
        gsonBuilder.registerTypeAdapter(SearchResults.class,new SearchModelJsonAdapter<Experience>(Experience.class));
        gsonBuilder.registerTypeAdapter(SearchResults.class,new SearchModelJsonAdapter<Data>(Data.class));
        gsonBuilder.registerTypeAdapter(SearchResults.class,new SearchModelJsonAdapter<Feed>(Feed.class));
        gsonBuilder.registerTypeAdapter(SearchResults.class,new SearchModelJsonAdapter<Message>(Message.class));

    }

    public GsonBuilder getGsonBuilder() {
        return gsonBuilder;
    }


}

package com.scala.exp.android.sdk;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.scala.exp.android.sdk.model.ContentNode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exp.android.sdk.R;

import com.scala.exp.android.sdk.channels.IChannel;
import com.scala.exp.android.sdk.model.Experience;
import com.scala.exp.android.sdk.model.Feed;
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.SearchResults;
import com.scala.exp.android.sdk.model.Thing;
import com.shopify.buy.dataprovider.BuyClient;
import com.shopify.buy.dataprovider.BuyClientFactory;
import com.shopify.buy.model.Collection;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Subscriber;
import rx.functions.Action1;


public class MainActivity extends ActionBarActivity {


    private String uuid = "caffba04-47a4-4575-a9e1-e6cdbce0f7ee";
    private String secret = "6fc5013fa0ea4fffb7ca7263916bd9f214b3d8c9e042d667043b1662916e5e6c4e99f16afb07b340ecee5f6e3ca4fbdb";
    private String user = "cesar.oyarzun@scala.com";
    private String password = "5715031Com";
    private String org = "scala";
    public static final String host = "https://api-develop.exp.scala.com";
    public static final String LIMIT = "limit";
    public static final String SKIP = "skip";
    public static final String SORT = "sort";
    private final String LOG_TAG = MainActivity.class.getSimpleName();


    // Constants
    public static final String BUY_CLIENT_SHOP = "my-shop.myshopify.com";
    public static final String BUY_CLIENT_API_KEY = "api key goes here";
    public static final String BUY_CLIENT_CHANNEL = "channel goes here";
    // Replace BUY_CLIENT_APP_NAME with whatever you like. We suggest
// using your applications bundle identifier
    public static final String BUY_CLIENT_APP_NAME = "COM.YOUR.APP.PACKAGE";

    // Easily access an instance of your Buy Client
    public static BuyClient newInstance() {
        BuyClient client = BuyClientFactory.getBuyClient(BUY_CLIENT_SHOP,
                BUY_CLIENT_API_KEY,
                BUY_CLIENT_CHANNEL,
                BUY_CLIENT_APP_NAME);
        return client;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppSingleton.getInstance().setHost(host);
        final Map<String,String> options = new HashMap<>();
        options.put(LIMIT, "10");
        options.put(SKIP, "0");
        options.put(SORT, "name");

        final Map<String,String> options2 = new HashMap<>();
        options2.put("host", "https://api-develop.exp.scala.com");
        options2.put("networkUuid", "8e72c821-4678-4540-9f43-b13f26b168d6");
        options2.put("apiKey", "40aa4fcee35332714087955793d53d20e830be6f7b7277d35ab32e5045b73aca33dfc6f9f1bb9239efdfa6d048bbb205");

        BuyClient client = newInstance();
        client.getCollections(new Callback<List<Collection>>() {
            @Override
            public void success(List<Collection> collections, Response response) {
                Log.i(LOG_TAG, collections.toString());
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e(LOG_TAG, error.toString());
            }
        });

        Exp.start(options2)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean o) {

                       Subscriber currentExperienceSubs = new Subscriber<JSONObject>() {
                           @Override
                           public void onCompleted() {}
                           @Override
                           public void onError(Throwable e) {Log.d(LOG_TAG, e.toString());}
                           @Override
                           public void onNext(JSONObject o) {
                               try {
                                   Object target = o.get("target");
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                               Log.d(LOG_TAG, o.toString());
                           }
                       };

                        Subscriber socketConnection = new Subscriber() {
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onNext(Object o) {
                                Log.d(LOG_TAG, o.toString());
                            }
                        };
                        Exp.connection(Utils.ONLINE,socketConnection);
                        Exp.getCurrentExperience(currentExperienceSubs);

                        IChannel channel = Exp.getChannel(Utils.SOCKET_CHANNELS.SYSTEM);
                        channel.fling("1111");

                        Exp.getThing("052a2419-0621-45ad-aa03-3747dbfe2b6d")
                                .then(new Subscriber<Thing>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("error", e.toString());
                                    }

                                    @Override
                                    public void onNext(Thing thing) {
                                        Object zones = thing.get("location.zones");
                                        Log.i("Response", thing.toString());
                                    }
                                });

                        Exp.findExperiences(options)
                                .then(new Subscriber<SearchResults<Experience>>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("error", e.toString());
                                    }

                                    @Override
                                    public void onNext(SearchResults<Experience> resultExperience) {
                                        Log.i("Response", resultExperience.toString());
                                    }
                                });

                        Exp.findLocations(options)
                                .then(new Subscriber<SearchResults<Location>>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("error", e.toString());
                                    }

                                    @Override
                                    public void onNext(SearchResults<Location> resultLocation) {
                                        Log.i("Response", resultLocation.toString());
                                    }
                                });

                        Exp.findFeeds(options)
                                .then(new Subscriber<SearchResults<Feed>>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("error", e.toString());
                                    }

                                    @Override
                                    public void onNext(SearchResults<Feed> feeds) {
                                        Log.i("Feeds", feeds.toString());
                                        for (Feed feed : feeds.getResults()) {
                                            Log.i("Feed", feed.getString("name"));
                                        }
                                    }
                                });

                        Exp.getContentNode("d24c6581-f3d2-4d5a-b6b8-e90a4812d7df")
                                .then(new Subscriber<ContentNode>() {
                                    @Override
                                    public void onCompleted() {}
                                    @Override
                                    public void onError(Throwable e) {Log.e("error", e.toString());}

                                    @Override
                                    public void onNext(ContentNode contentNode) {
                                        Log.i("Response", contentNode.toString());
                                        contentNode.getChildren().then(new Subscriber<List<ContentNode>>() {

                                            @Override
                                            public void onCompleted() {
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.e("error", e.toString());
                                            }

                                            @Override
                                            public void onNext(List<ContentNode> children) {
                                                for (ContentNode child : children) {
                                                    Log.i("child", String.valueOf(child.get("name")));
                                                    child.getChildren().then(new Subscriber<List<ContentNode>>() {
                                                        @Override
                                                        public void onCompleted() {
                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                            Log.e("error", e.toString());
                                                        }

                                                        @Override
                                                        public void onNext(List<ContentNode> grandchildren) {
                                                            for (ContentNode grandchild : grandchildren) {
                                                                Log.i("grandchild", String.valueOf(grandchild.get("name")));
                                                            }
                                                        }
                                                    });
                                                }
                                            }

                                        });
                                    }
                                });

                        Exp.findContentNodes(options)
                                .then(new Subscriber<SearchResults<ContentNode>>() {
                                    @Override
                                    public void onCompleted() {}
                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        Log.e("error", e.toString());
                                    }

                                    @Override
                                    public void onNext(SearchResults<ContentNode> contentNodes) {
                                        Log.i("Response", contentNodes.toString());
                                    }
                                });
                    }

                });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

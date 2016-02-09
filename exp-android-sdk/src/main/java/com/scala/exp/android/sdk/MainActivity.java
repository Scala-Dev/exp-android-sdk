package com.scala.exp.android.sdk;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.scala.exp.android.sdk.channels.IChannel;
import com.scala.exp.android.sdk.model.ContentNode;
import com.scala.exp.android.sdk.model.Experience;
import com.scala.exp.android.sdk.model.Feed;
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.SearchResults;
import com.scala.exp.android.sdk.model.Thing;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exp.android.sdk.R;
import rx.Subscriber;
import rx.functions.Action1;


public class MainActivity extends ActionBarActivity {


    private String uuid = "caffba04-47a4-4575-a9e1-e6cdbce0f7ee";
    private String secret = "6fc5013fa0ea4fffb7ca7263916bd9f214b3d8c9e042d667043b1662916e5e6c4e99f16afb07b340ecee5f6e3ca4fbdb";
    private String user = "cesar.oyarzun@scala.com";
//    private String password = "Com5715031@";
     private String password = "5715031Com";

    private String org = "";
//    public static final String host = "https://api.goexp.io";

    public static final String host = "http://192.168.1.4:9000";
//    public static final String host = "http://192.168.30.193:9000";
    public static final String LIMIT = "limit";
    public static final String SKIP = "skip";
    public static final String SORT = "sort";
    private final String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppSingleton.getInstance().setHost(host);
        final Map<String,String> options = new HashMap<>();
        options.put(LIMIT, "10");
        options.put(SKIP, "0");
        options.put(SORT, "name");
        final Map<String,String> startOptions = new HashMap<>();
        startOptions.put(Utils.HOST,host);
        startOptions.put(Utils.USERNAME,user);
        startOptions.put(Utils.PASSWORD,password);
        startOptions.put(Utils.ORGANIZATION,org);
        startOptions.put("enableEvents","true");
        Log.i(LOG_TAG, "START EXP SDK");
        Exp.start(startOptions)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean o) {

                        Subscriber currentExperienceSubs = new Subscriber<JSONObject>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(LOG_TAG, e.toString());
                            }

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
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onNext(Object o) {
                                Log.d(LOG_TAG, o.toString());
                            }
                        };

                        final IChannel channel1 = Exp.getChannel("channel1",0,1);
                        final Map<String, Object> payload = new HashMap<String, Object>();

                        payload.put("cesar", "oyarzun");
                        channel1.listen("hi", new Subscriber() {
                            @Override
                            public void onCompleted() {}

                            @Override
                            public void onError(Throwable e) {
                                Log.e("Error", e.getMessage());
                            }

                            @Override
                            public void onNext(Object o) {
                                Log.d("REsponse", o.toString());
                            }
                        }).subscribe(new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {Log.e("ONCOMPLETED", "");}
                            @Override
                            public void onError(Throwable e) {Log.e("ERROR", e.getMessage());}
                            @Override
                            public void onNext(Object o) {
                                Log.e("ONNEXT", o.toString());
                                channel1.broadcast("hi", payload, 2000);
                            }
                        });


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

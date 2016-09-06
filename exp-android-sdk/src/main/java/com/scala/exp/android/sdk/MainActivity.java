package com.scala.exp.android.sdk;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.scala.exp.android.sdk.channels.IChannel;
import com.scala.exp.android.sdk.model.Content;
import com.scala.exp.android.sdk.model.Experience;
import com.scala.exp.android.sdk.model.SearchResults;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import exp.android.sdk.R;
import rx.Subscriber;


public class MainActivity extends ActionBarActivity {


    private String uuid = "caffba04-47a4-4575-a9e1-e6cdbce0f7ee";
    private String secret = "6fc5013fa0ea4fffb7ca7263916bd9f214b3d8c9e042d667043b1662916e5e6c4e99f16afb07b340ecee5f6e3ca4fbdb";
    private String user = "cesar.oyarzun@scala.com";
//    private String password = "Com5715031@";
//     private String password = "5715031Com";
    private String password = "5715031Com@";

    private String org = "";
//    public static final String host = "https://api.goexp.io";
    public static final String host =  "https://api-staging.goexp.io";

//    public static final String host = "http://192.168.1.4:9000";
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
        final Map<String,Object> options = new HashMap<>();
        options.put(LIMIT, 10);
        options.put(SKIP, 0);
        options.put(SORT, "name");
        final Map<String,Object> startOptions = new HashMap<>();
        startOptions.put(Utils.HOST,host);
        startOptions.put(Utils.USERNAME,user);
        startOptions.put(Utils.PASSWORD,password);
        startOptions.put(Utils.ORGANIZATION,org);
        startOptions.put(Utils.ENABLE_EVENTS,true);
        Log.i(LOG_TAG, "START EXP SDK");
        Exp.start(startOptions)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

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

                        final IChannel channel1 = Exp.getChannel("channel1",false,true);
                        final Map<String, Object> payload = new HashMap<String, Object>();

                        payload.put("cesar", "oyarzun");
                        channel1.listen("hi", new Subscriber() {
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) {Log.e("Error", e.getMessage());}
                            @Override
                            public void onNext(Object o) {
                                Log.d("REsponse", o.toString());
                            }
                        }).subscribe(new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {
                                Log.e("ONCOMPLETED", "");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("ERROR", e.getMessage());
                            }

                            @Override
                            public void onNext(Object o) {
                                Log.e("ONNEXT", o.toString());
                                channel1.broadcast("hi", payload, 2000);
                            }
                        });

//                        Exp.getLocation("6d042ccc-ed59-4521-a8fb-b7e6ce198e72").then(new Subscriber<Location>() {
//                            @Override
//                            public void onCompleted() {
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                            }
//
//                            @Override
//                            public void onNext(Location location) {
//                                List<Zone> zones = location.getZones();
//                                location.getDevices().then(new Subscriber<SearchResults<Device>>() {
//                                    @Override
//                                    public void onCompleted() {
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        Log.e("error", e.toString());
//                                    }
//
//                                    @Override
//                                    public void onNext(SearchResults<Device> resultExperience) {
//                                        Log.i("Response", resultExperience.toString());
//                                    }
//                                });
//                            }
//                        });
//
//                        Exp.getThing("052a2419-0621-45ad-aa03-3747dbfe2b6d")
//                                .then(new Subscriber<Thing>() {
//                                    @Override
//                                    public void onCompleted() {
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        Log.e("error", e.toString());
//                                    }
//
//                                    @Override
//                                    public void onNext(Thing thing) {
//                                        Object zones = thing.get("location.zones");
//                                        Log.i("Response", thing.toString());
//                                    }
//                                });
//                        Exp.getDevice("31da0638-507e-4ac7-8b66-7fd5a8734a05")
//                                .then(new Subscriber<Device>() {
//                                    @Override
//                                    public void onCompleted() {
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        Log.e("error", e.toString());
//                                    }
//
//                                    @Override
//                                    public void onNext(Device device) {
//                                        Location location = device.getLocation();
//                                        List<Zone> zones = device.getZones();
//                                        Experience experience = device.getExperience();
//                                        Log.i("Response", device.toString());
//                                    }
//                                });
//
//                        Exp.findExperiences(options)
//                                .then(new Subscriber<SearchResults<Experience>>() {
//                                    @Override
//                                    public void onCompleted() {
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        Log.e("error", e.toString());
//                                    }
//
//                                    @Override
//                                    public void onNext(SearchResults<Experience> resultExperience) {
//                                        Log.i("Response", resultExperience.toString());
//                                    }
//                                });
//
//                        Exp.findLocations(options)
//                                .then(new Subscriber<SearchResults<Location>>() {
//                                    @Override
//                                    public void onCompleted() {
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        Log.e("error", e.toString());
//                                    }
//
//                                    @Override
//                                    public void onNext(SearchResults<Location> resultLocation) {
//                                        Log.i("Response", resultLocation.toString());
//                                    }
//                                });
//
//                        Exp.findFeeds(options)
//                                .then(new Subscriber<SearchResults<Feed>>() {
//                                    @Override
//                                    public void onCompleted() {
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        Log.e("error", e.toString());
//                                    }
//
//                                    @Override
//                                    public void onNext(SearchResults<Feed> feeds) {
//                                        Log.i("Feeds", feeds.toString());
//                                        for (Feed feed : feeds.getResults()) {
//                                            Log.i("Feed", feed.getString("name"));
//                                            Map<String,Object> query = new HashMap<String, Object>();
//                                            query.put("name","scala");
//                                            feed.getData(query).then(new Subscriber<Map>() {
//                                                @Override
//                                                public void onCompleted() {}
//                                                @Override
//                                                public void onError(Throwable e) {
//                                                    Log.i("Feed", e.getMessage());
//                                                }
//
//                                                @Override
//                                                public void onNext(Map feedData) {
//                                                    Log.i("Feed", feedData.toString());
//                                                }
//                                            });
//                                        }
//                                    }
//                                });

                        Exp.getContent("d1f4debd-00d7-4211-8c8c-2ba00c2e8143")
                                .then(new Subscriber<Content>() {
                                    @Override
                                    public void onCompleted() {}
                                    @Override
                                    public void onError(Throwable e) {Log.e("error", e.toString());}

                                    @Override
                                    public void onNext(final Content contentNode) {
                                        Log.i("Response", contentNode.toString());
//                                        Map options = new HashMap();
//                                        options.put("subtype","folder");
                                        contentNode.getChildren(new HashMap()).then(new Subscriber<SearchResults<Content>>() {
                                            @Override
                                            public void onCompleted() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onNext(SearchResults<Content> contentSearchResults) {
                                                Log.i("Response", contentSearchResults.toString());
                                                for (Content child : contentSearchResults) {
                                                    Log.i("Response", child.toString());
                                                }
                                            }
                                        });

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
                                        for (Experience child : resultExperience) {
                                            Log.i("Response", child.toString());
                                        }
                                    }
                                });


//                        Exp.findContent(options)
//                                .then(new Subscriber<SearchResults<Content>>() {
//                                    @Override
//                                    public void onCompleted() {}
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        e.printStackTrace();
//                                        Log.e("error", e.toString());
//                                    }
//
//                                    @Override
//                                    public void onNext(SearchResults<Content> contentNodes) {
//                                        Log.i("Response", contentNodes.toString());
//                                    }
//                                });

                        Subscriber updateSubscriber = new Subscriber<String>() {
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onNext(String o) {
                                Log.d(LOG_TAG, "UPDATE LOGING");
                            }
                        };
                        Exp.on("update", updateSubscriber);
                    }
                    @Override
                    public void onError(Throwable e) {Log.e("ERROR", e.getMessage());}

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.d(LOG_TAG, "ONNEXT");
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

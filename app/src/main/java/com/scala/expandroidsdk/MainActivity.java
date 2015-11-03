package com.scala.expandroidsdk;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.scala.expandroidsdk.model.ContentNode;
import com.scala.expandroidsdk.model.Device;
import com.scala.expandroidsdk.model.ResultExperience;
import com.scala.expandroidsdk.model.ResultLocation;
import com.scala.expandroidsdk.model.ResultThing;
import com.scala.expandroidsdk.model.Thing;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;


public class MainActivity extends ActionBarActivity {

    private String uuid = "caffba04-47a4-4575-a9e1-e6cdbce0f7ee";
    private String secret = "6fc5013fa0ea4fffb7ca7263916bd9f214b3d8c9e042d667043b1662916e5e6c4e99f16afb07b340ecee5f6e3ca4fbdb";
    private String user = "cesar.oyarzun@scala.com";
    private String password = "5715031Com";
    private String org = "scala";
    public static final String host = "https://api-develop.exp.scala.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Exp.start(host, uuid, secret).then(new Subscriber<Object>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Object o) {
                Log.e("Response", o.toString());
            }
        });


//        Exp.start(host, uuid, secret).then(new Subscriber<Object>() {
//            @Override
//            public void onCompleted() {}
//
//            @Override
//            public void onError(Throwable e) {}
//
//            @Override
//            public void onNext(Object o) {
//                Log.e("Response", o.toString());
//            }
//        });

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
                        Object o = thing.get("location.zones");
                        Log.e("Response", thing.toString());
                    }
                });

        Exp.getDevice("caffba04-47a4-4575-a9e1-e6cdbce0f7ee")
                .then(new Subscriber<Device>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onNext(Device thing) {
                        Object o = thing.get("location.zones");
                        Log.e("Response", thing.toString());
                    }
                });


        Map<String,String> optionsThings = new HashMap<>();
        optionsThings.put("limit", "10");
        optionsThings.put("skip", "0");
        optionsThings.put("sort", "asc");

        Exp.findthings(optionsThings).then(new Subscriber<ResultThing>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(ResultThing resultThing) {
                Log.e("Response", resultThing.toString());
            }
        });


        Map<String,String> optionsExp = new HashMap<>();
        optionsExp.put("limit","10");
        optionsExp.put("skip", "0");
        optionsExp.put("sort", "asc");

        Exp.findExperiences(optionsExp).then(new Subscriber<ResultExperience>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(ResultExperience resultExperience) {
                Log.e("Response", resultExperience.toString());
            }
        });


        Map<String,String> optionsLoc = new HashMap<>();
        optionsLoc.put("limit","10");
        optionsLoc.put("skip","0");
        optionsLoc.put("sort", "asc");

        Exp.findLocation(optionsLoc).then(new Subscriber<ResultLocation>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(ResultLocation resultExperience) {
                Log.e("Response", resultExperience.toString());
            }
        });

        Exp.getContentNode("d24c6581-f3d2-4d5a-b6b8-e90a4812d7df")
                .then(new Subscriber<ContentNode>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onNext(ContentNode thing) {
                        Log.e("Response", thing.toString());
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

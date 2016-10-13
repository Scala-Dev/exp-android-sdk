package com.scala.exp.android.sdk;


import android.util.Log;

import com.scala.exp.android.sdk.channels.Channel;
import com.scala.exp.android.sdk.channels.ChannelFactory;
import com.scala.exp.android.sdk.channels.IChannel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.ReplaySubject;


/**
 * Created by Cesar Oyarzun on 11/4/15.
 */
public class SocketManager {

    public static final String TLS = "TLS";
    private final String LOG_TAG = SocketManager.class.getSimpleName();
    private Socket socket;

    private Map<String,Subscriber> connection = new HashMap<>();
    private Map<String,IChannel> channelCache = new HashMap<>();
    private Map<String,IChannel> channels = new HashMap<>();
    private List<String> subscription = new ArrayList<>();
    private Map<String,ReplaySubject> subscriptionPromise = new HashMap<>();

    /**
     * Start Socket Connection
     * @return
     */
    public Observable<Boolean> startSocket() {
        Log.d(LOG_TAG,"Starting EXP Socket Channels...");
        if (socket == null) {
            try {
                SSLContext sc = SSLContext.getInstance(TLS);
                sc.init(null, trustAllCerts, new SecureRandom());
                IO.setDefaultSSLContext(sc);
                HttpsURLConnection.setDefaultHostnameVerifier(new RelaxedHostNameVerifier());

                // socket options
                IO.Options opts = new IO.Options();
                opts.forceNew = true;
                opts.reconnection = true;
                opts.secure = true;
                opts.sslContext = sc;
                opts.query = "token="+ AppSingleton.getInstance().getToken();

                socket = IO.socket(AppSingleton.getInstance().getHostSocket(), opts);
//                socket = IO.socket("http://192.168.30.193:9002", opts);
//                socket = IO.socket("http://192.168.1.4:9002", opts);

                socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.d(LOG_TAG,"EXP socket connected");
                        //do subscribe channels
                        subscribeChannels();
                        if(connection.containsKey(Utils.ONLINE)){
                            Subscriber subscriber = connection.get(Utils.ONLINE);
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        }
                    }

                }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.d(LOG_TAG, "EXP socket disconnected");
                        if (connection.containsKey(Utils.OFFLINE)) {
                            Subscriber subscriber = connection.get(Utils.OFFLINE);
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        }
                    }

                }).on(Utils.BROADCAST, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.d(LOG_TAG,"EXP broadcast =" +args);
                        JSONObject jsonObject = (JSONObject) args[0];
                        try {
                            String channelId = (String) jsonObject.get("channel");
                            if(channels.containsKey(channelId)){
                                IChannel channel = channels.get(channelId);
                                channel.onBroadCast(jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }).on(Utils.SUBSCRIBED, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.d(LOG_TAG,"EXP subscribed ="+args);
                        final JSONArray json = (JSONArray) args[0];
                        for(int i = 0 ; i < json.length(); i++){
                            try {
                                String idChannel = (String) json.get(i);
                                ReplaySubject replaySubject = subscriptionPromise.get(idChannel);
                                replaySubject.onNext(idChannel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.d(LOG_TAG, "error: " + args[0].toString());}

                });
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }

        // Connect if disconnected
        if (!socket.connected()) {
            Log.d(LOG_TAG, "Exp Connecting with Socket...");
            socket.connect();
        } else {
            Log.d(LOG_TAG, "Exp Socket Connected");
        }
        return Observable.just(true);
    }



    /**
     * Trust all Certificates
     */
    private TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[] {};
        }

        public void checkClientTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }
    } };


    /**
     * Subscribe channels to socket connection
     * @param channelId
     * @return
     */
    public ReplaySubject<Object> subscribe(final String channelId) {
        if(!subscription.contains(channelId)){
            subscription.add(channelId);
            socket.emit("subscribe",new JSONArray(subscription));
        }
        ReplaySubject<Object> observable = ReplaySubject.create();
        subscriptionPromise.put(channelId,observable);
        return observable;
    }


    /**
     * Host Name Verifier,is set to default true
     */
    private static class RelaxedHostNameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * Create SSL certificates for socket connection
     * @return
     */
    private SSLContext ceateSsl() {
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance(TLS);
            sc.init(null, trustAllCerts, new SecureRandom());
            IO.setDefaultSSLContext(sc);
            HttpsURLConnection.setDefaultHostnameVerifier(new RelaxedHostNameVerifier());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sc;
    }



    /**
     * Get Channel from string
     * @param channel
     * @return
     */
    public IChannel getChannel(String channel, boolean system, boolean consumerApp){
        Channel expChannel = null;
        if(channelCache.get(channel)!= null){
            expChannel = (Channel) channelCache.get(channel);
        }else{
            expChannel = (Channel)ChannelFactory.createChannel(channel,this,system,consumerApp);
            channels.put(expChannel.generateId(),expChannel);
            channelCache.put(channel,expChannel);
        }
        return expChannel;
    }


    /**
     * Connection subscriber for socket state
     * @param name
     * @param subscriber
     */
    public void connection(String name, Subscriber subscriber){
        connection.put(name,subscriber);
    }

    /**
     * Disconnect EXP clean token
     */
    public void disconnect(){
        socket.disconnect();
        AppSingleton.getInstance().setToken("");
        AppSingleton.getInstance().setHostSocket("");
        AppSingleton.getInstance().setAuth(null);
        channelCache = new HashMap<>();
        channels = new HashMap<>();
        subscription = new ArrayList<>();
        subscriptionPromise = new HashMap<>();
    }

    /**
     * Refresh connection
     */
    public void refreshConnection(){
        socket.close();
        socket.connect();
        subscribeChannels();
        Log.d(LOG_TAG,"EXP refresh socket connection");
    }

    /**
     * Subscribe channels when there is a refresh connection
     */
    public void subscribeChannels(){
        this.subscription = new ArrayList<>();
        this.subscriptionPromise = new HashMap<>();
        for (String channelKey:channels.keySet()) {
               subscribe(channelKey);
        }
    }

    /**
     * Check if socket is connected
     * @return
     */
    public boolean isConnected(){
        return socket.connected();
    }
}
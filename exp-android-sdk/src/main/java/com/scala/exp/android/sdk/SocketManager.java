package com.scala.exp.android.sdk;


import android.util.Log;

import com.scala.exp.android.sdk.channels.CommonChannel;
import com.scala.exp.android.sdk.channels.ExperienceChannel;
import com.scala.exp.android.sdk.channels.IChannel;
import com.scala.exp.android.sdk.channels.LocationChannel;
import com.scala.exp.android.sdk.channels.OrganizationChannel;
import com.scala.exp.android.sdk.channels.SystemChannel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.*;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import rx.Observable;
import rx.Subscriber;


/**
 * Created by Cesar Oyarzun on 11/4/15.
 */
public class SocketManager {

    public static final String TLS = "TLS";
    private final String LOG_TAG = SocketManager.class.getSimpleName();
    private Socket socket;
    private OrganizationChannel organizationChannel = null;
    private SystemChannel systemChannel = null;
    private LocationChannel locationChannel = null;
    private ExperienceChannel experienceChannel = null;
    private Map<String,Subscriber> connection = new HashMap<>();
    private Map<String,IChannel> channelCache = new HashMap<>();

    /**
     * Start Socket Connection
     * @return
     */
    public Observable<Boolean> startSocket() {

        if (socket == null) {
            try {
                SSLContext sc = SSLContext.getInstance(TLS);
                sc.init(null, trustAllCerts, new SecureRandom());
                IO.setDefaultSSLContext(sc);
                HttpsURLConnection.setDefaultHostnameVerifier(new RelaxedHostNameVerifier());

                // socket options
                IO.Options opts = new IO.Options();
                opts.forceNew = true;
                opts.reconnection = false;
                opts.secure = true;
                opts.sslContext = sc;
                opts.query = "token="+AppSingleton.getInstance().getToken();

                socket = IO.socket(AppSingleton.getInstance().getHost(), opts);

                //create channels
                organizationChannel = new OrganizationChannel(socket);
                systemChannel = new SystemChannel(socket);
                locationChannel = new LocationChannel(socket);
                experienceChannel = new ExperienceChannel(socket);

                socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        if(connection.containsKey(Utils.ONLINE)){
                            Subscriber subscriber = connection.get(Utils.ONLINE);
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        }
                    }

                }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        if(connection.containsKey(Utils.OFFLINE)){
                            Subscriber subscriber = connection.get(Utils.OFFLINE);
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        }
                    }

                }).on(Utils.MESSAGE, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        final JSONObject json = (JSONObject) args[0];
                        try {
                            String type = json.getString(Utils.TYPE);
                            String channel = null;
                            if(json.has(Utils.CHANNEL)){
                                channel = json.getString(Utils.CHANNEL);
                            }
                            if(Utils.RESPONSE.equalsIgnoreCase(type)){
                                handleResponse(json, channel);
                            }else if(Utils.REQUEST.equalsIgnoreCase(type)){
                                handleRequest(json, channel);
                            }else if(Utils.BROADCAST.equalsIgnoreCase(type)){
                                handleBroadcast(json, channel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {Log.d(LOG_TAG, "error: " + args[0].toString());}

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
            Log.d(LOG_TAG, "Connecting with Socket...");
            socket.connect();
        } else {
            Log.d(LOG_TAG, "Socket Connected");
        }
        return Observable.just(true);
    }

    /**
     * Handle BroadCast event bus
     * @param json
     * @param channel
     * @throws JSONException
     */
    private void handleBroadcast(JSONObject json, String channel) throws JSONException {
        if(channel == null){
            systemChannel.onBroadCast(json);
            experienceChannel.onBroadCast(json);
            locationChannel.onBroadCast(json);
            organizationChannel.onBroadCast(json);
        }else{
            Utils.SOCKET_CHANNELS socket_channels = Utils.getSocketEnum(channel);
            if(socket_channels!=null){
                switch (socket_channels){
                    case SYSTEM:
                        systemChannel.onBroadCast(json);
                        break;
                    case ORGANIZATION:
                        organizationChannel.onBroadCast(json);
                        break;
                    case LOCATION:
                        locationChannel.onBroadCast(json);
                        break;
                    case EXPERIENCE:
                        experienceChannel.onBroadCast(json);
                        break;
                }
            }else if(channelCache.get(channel) != null) {
                IChannel commonChannel = channelCache.get(channel);
                commonChannel.onBroadCast(json);
            }

        }
    }

    /**
     * Handle Request event bus
     * @param json
     * @param channel
     * @throws JSONException
     */
    private void handleRequest(JSONObject json, String channel) throws JSONException {
        if(channel == null){
            systemChannel.onRequest(json);
            experienceChannel.onRequest(json);
            locationChannel.onRequest(json);
            organizationChannel.onRequest(json);
        }else{
            Utils.SOCKET_CHANNELS socket_channels = Utils.getSocketEnum(channel);
            if( socket_channels != null ){
                switch (socket_channels){
                    case SYSTEM:
                        systemChannel.onRequest(json);
                        break;
                    case ORGANIZATION:
                        organizationChannel.onRequest(json);
                        break;
                    case LOCATION:
                        locationChannel.onRequest(json);
                        break;
                    case EXPERIENCE:
                        experienceChannel.onRequest(json);
                        break;
                }
            }else if( channelCache.get(channel) != null ){
                IChannel commonChannel = channelCache.get(channel);
                commonChannel.onRequest(json);
            }

        }
    }

    /**
     * Handle Response event bus
     * @param json
     * @param channel
     * @throws JSONException
     */
    private void handleResponse(JSONObject json, String channel) throws JSONException {
        if(channel == null){
            systemChannel.onResponse(json);
            experienceChannel.onResponse(json);
            locationChannel.onResponse(json);
            organizationChannel.onResponse(json);
        }else{
            Utils.SOCKET_CHANNELS socket_channels = Utils.getSocketEnum(channel);
            if( socket_channels != null){
                switch (socket_channels){
                    case SYSTEM:
                        systemChannel.onResponse(json);
                        break;
                    case ORGANIZATION:
                        organizationChannel.onResponse(json);
                        break;
                    case LOCATION:
                        locationChannel.onResponse(json);
                        break;
                    case EXPERIENCE:
                        experienceChannel.onResponse(json);
                        break;
                }
            }else if(channelCache.get(channel)!=null){
                IChannel commonChannel = channelCache.get(channel);
                commonChannel.onResponse(json);
            }

        }
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
     * Get current Experience
     * @param callback
     * @throws JSONException
     */
    public void getCurrentExperience(Subscriber callback) throws JSONException {
        Map<String,String> message = new HashMap<>();
        message.put(Utils.TYPE,Utils.REQUEST);
        message.put(Utils.NAME, Utils.GET_CURRENT_EXPERIENCE);
        systemChannel.request(message, callback);
    }

    /**
     * Get Current Device
     * @param callback
     * @throws JSONException
     */
    public void getCurrentDevice(Subscriber callback) throws JSONException {
        Map<String,String> message = new HashMap<>();
        message.put(Utils.TYPE, Utils.REQUEST);
        message.put(Utils.NAME, Utils.GET_CURRENT_DEVICE);
        systemChannel.request(message, callback);
    }

    /**
     * Get Channel from Enum
     * @param channel
     * @return
     */
    public IChannel getChannel(Utils.SOCKET_CHANNELS channel){
        IChannel expChannel = null;
        switch (channel){
            case SYSTEM:
                expChannel = this.systemChannel;
                break;
            case ORGANIZATION:
                expChannel = this.organizationChannel;
                break;
            case LOCATION:
                expChannel = this.organizationChannel;
                break;
            case EXPERIENCE:
                expChannel = this.organizationChannel;
                break;
        }
        return expChannel;
    }


    /**
     * Get Channel from string
     * @param channel
     * @return
     */
    public IChannel getChannel(String channel){
        IChannel expChannel = null;
        if(channelCache.get(channel)!= null){
            expChannel = channelCache.get(channel);
        }else{
            expChannel = new CommonChannel(socket,channel);
            channelCache.put(channel,expChannel);
        }
        return expChannel;
    }


    /**
     * Connection subscriber for socket state
     * @param name
     * @param subscriber
     */
    public void connection(String name,Subscriber subscriber){
        connection.put(name,subscriber);
    }

    /**
     * Disconnect EXP clean token
     */
    public void disconnect(){
        socket.disconnect();
        AppSingleton.getInstance().setToken("");
        channelCache = new HashMap<>();
    }

    /**
     * Refresh connection
     */
    public void refreshConnection(){
        socket.close();
        socket.connect();
    }
}
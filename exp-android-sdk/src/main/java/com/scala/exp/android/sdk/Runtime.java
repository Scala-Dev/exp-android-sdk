package com.scala.exp.android.sdk;

import com.scala.exp.android.sdk.model.Token;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar Oyarzun on 11/2/15.
 */
public class Runtime extends Exp{

    /**
     * Start with device credentials Host,UUID,Secret TODO make this ExpObservable
     * @param host
     * @param uuid
     * @param secret
     * @return
     */
    public static Observable<Boolean> start(String host, String uuid, String secret){
        Map<String,Object> header = new HashMap<String,Object>();
        header.put(Utils.TYP, Utils.JWT);
        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put(Utils.UUID, uuid);
        String token = Jwts.builder().setHeader(header).setClaims(payload).signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
        AppSingleton.getInstance().setToken(token);
        return  ExpService.init(host,token).flatMap(new Func1<Boolean, Observable<Boolean>>() {
            @Override
            public Observable call(Boolean aBoolean) {
                socketManager = new SocketManager();
                return socketManager.startSocket();
            }
        });
    }

    protected static Observable<Boolean> startConsumerApp(String host, String uuid, String secret){
        Map<String,Object> header = new HashMap<String,Object>();
        header.put(Utils.TYP, Utils.JWT);
        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put(Utils.NETWORK_UUID, uuid); // TODO change to CONSUMER_APP_UUID
        String token = Jwts.builder().setHeader(header).setClaims(payload).signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
        AppSingleton.getInstance().setToken(token);
        return  ExpService.init(host,token).flatMap(new Func1<Boolean, Observable<Boolean>>() {
            @Override
            public Observable call(Boolean aBoolean) {
                socketManager = new SocketManager();
                return socketManager.startSocket();
            }
        });
    }


    /**
     * Start with  options
     * @param options
     * @return
     */
    public static Observable<Boolean> start(Map<String,String> options){
        Observable observable = null;
        String hostUrl = "";
        if(options.get(Utils.HOST)!=null){
            hostUrl = options.get(Utils.HOST);
        }
        if(options.get(Utils.USERNAME)!= null && options.get(Utils.PASSWORD)!= null && options.get(Utils.ORGANIZATION)!= null){
            observable = start(hostUrl,options.get(Utils.USERNAME),options.get(Utils.PASSWORD),options.get(Utils.ORGANIZATION));
        }else if(options.get(Utils.UUID)!= null && options.get(Utils.SECRET)!= null){
            observable = start(hostUrl,options.get(Utils.UUID),options.get(Utils.SECRET));
        }else if(options.get(Utils.DEVICE_UUID)!= null && options.get(Utils.SECRET)!= null){
            observable = start(hostUrl,options.get(Utils.DEVICE_UUID),options.get(Utils.SECRET));
        }else if(options.get(Utils.NETWORK_UUID)!= null && options.get(Utils.API_KEY)!= null){
            observable = startConsumerApp(hostUrl, options.get(Utils.NETWORK_UUID), options.get(Utils.API_KEY));
        }else if(options.get(Utils.CONSUMER_APP_UUID)!= null && options.get(Utils.API_KEY)!= null){
            observable = startConsumerApp(hostUrl, options.get(Utils.CONSUMER_APP_UUID), options.get(Utils.API_KEY));
        } else {
            throw new RuntimeException("Credentials are missing from start call");
        }

        return observable;
    }

    /**
     * Start with user credentials TODO make this ExpObservable
     * @param host
     * @param user
     * @param password
     * @param organization
     * @return
     */
    public static Observable<Boolean> start(final String host, String user, String password, String organization){
        final Map<String,String> options = new HashMap<String,String>();
        options.put(Utils.USERNAME,user);
        options.put(Utils.PASSWORD,password);
        options.put(Utils.ORG, organization);

        return ExpService.init(host)
                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean result) {

                        return Exp.login(options)
                                .flatMap(new Func1<Token, Observable<Boolean>>() {
                                    @Override
                                    public Observable<Boolean> call(Token token) {
                                        AppSingleton.getInstance().setToken(token.getToken());
                                        AppSingleton.getInstance().setHost(host);
                                        return ExpService.init(host, token.getToken())
                                                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                                                    @Override
                                                    public Observable call(Boolean aBoolean) {
                                                        socketManager = new SocketManager();
                                                        return socketManager.startSocket();
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    /**
     * Stop Connection to EXP
     */
    public static void stop(){
        socketManager.disconnect();
    }

}

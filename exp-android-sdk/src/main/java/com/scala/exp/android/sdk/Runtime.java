package com.scala.exp.android.sdk;

import android.util.Log;

import com.scala.exp.android.sdk.model.Auth;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Cesar Oyarzun on 11/2/15.
 */
public class Runtime extends Exp{
    private static final String LOG_TAG = Runtime.class.getSimpleName();

    private static Boolean enableSocket = true;


    /**
     * Start with device credentials Host,UUID,Secret
     * @param keyPayload
     * @param uuid
     * @param secret
     * @return String token
     */
    private static String createToken(String uuid, String secret, String keyPayload){
        Log.d(LOG_TAG, "EXP Create Token: [uuid=" + uuid + "]" + "[secret=" + secret + "]" + "[keyPaload=" + keyPayload + "]");
        Map<String,Object> header = new HashMap<String,Object>();
        header.put(Utils.TYP, Utils.JWT);
        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put(Utils.UUID, uuid);
        payload.put(Utils.TYPE, keyPayload);
        String compact = Jwts.builder().setHeader(header).setClaims(payload).signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
        Log.d(LOG_TAG, "EXP Created Token: "+compact);
        return compact;

    }

    /**
     * Start with  options
     * @param options
     * @return
     */
    public static Observable<Boolean> start(Map<String,Object> options){
        Log.d(LOG_TAG,"EXP start with options "+options);
        Observable<Boolean> observable = null;
        Map<String,Object> opts= new HashMap<>();
        String hostUrl = "";
        if(options.get(Utils.HOST) != null){
            AppSingleton.getInstance().setHost((String) options.get(Utils.HOST));
        }
        if(options.get(Utils.ENABLE_EVENTS) != null){
            enableSocket = (Boolean) options.get(Utils.ENABLE_EVENTS);
        }
        if(options.get(Utils.USERNAME) != null && options.get(Utils.PASSWORD) != null && options.get(Utils.ORGANIZATION) != null){
            observable = start_auth(options);
        }else if (options.get(Utils.UUID) != null && options.get(Utils.SECRET) != null) {
            opts.put(Utils.TOKEN,createToken((String) options.get(Utils.UUID), (String) options.get(Utils.SECRET),Utils.DEVICE));
            observable = start_auth(opts);
        } else if (options.get(Utils.DEVICE_UUID) != null && options.get(Utils.SECRET) != null) {
            opts.put(Utils.TOKEN,createToken((String) options.get(Utils.DEVICE_UUID), (String) options.get(Utils.SECRET),Utils.DEVICE));
            observable = start_auth(opts);
        } else if (options.get(Utils.NETWORK_UUID) != null && options.get(Utils.API_KEY) != null) {
            opts.put(Utils.TOKEN,createToken((String) options.get(Utils.NETWORK_UUID), (String) options.get(Utils.API_KEY),Utils.CONSUMER_APP));
            observable = start_auth(opts);
        } else if (options.get(Utils.CONSUMER_APP_UUID) != null && options.get(Utils.API_KEY) != null) {
            opts.put(Utils.TOKEN,createToken((String) options.get(Utils.CONSUMER_APP_UUID), (String) options.get(Utils.API_KEY), Utils.CONSUMER_APP));
            observable = start_auth(opts);
        } else if (options.get(Utils.UUID) != null && options.get(Utils.API_KEY) != null) {
            opts.put(Utils.TOKEN,createToken((String) options.get(Utils.UUID), (String) options.get(Utils.API_KEY),Utils.CONSUMER_APP));
            observable = start_auth(opts);
        } else if(options.get(Utils.AUTH)!=null){
            Auth auth = (Auth) options.get(Utils.AUTH);
            opts.put(Utils.TOKEN,auth.getToken());
            observable = start_auth(auth);
        } else {
            throw new RuntimeException("Credentials are missing from start call");
        }

        return observable;
    }

    /**
     * Start with options
     * @param options
     * @return
     */
    public static Observable<Boolean> start_auth(final Map<String, Object> options){
        return ExpService.init(AppSingleton.getInstance().getHost())
                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean result) {

                        return Exp.login(options)
                                .flatMap(new Func1<Auth, Observable<Boolean>>() {
                                    @Override
                                    public Observable<Boolean> call(Auth auth) {
                                        Log.d(LOG_TAG, "EXP login response :" + auth.getToken());
                                        AppSingleton.getInstance().setToken(auth.getToken());
                                        if (auth.getNetwork() != null) {
                                            String hostSocket = auth.getNetwork().getHost();
                                            AppSingleton.getInstance().setHostSocket(hostSocket);
                                        }
                                        AppSingleton.getInstance().setAuth(auth);

                                        final BigInteger expiration = auth.getExpiration();
                                        return ExpService.init(AppSingleton.getInstance().getHost(), auth.getToken())
                                                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                                                    @Override
                                                    public Observable call(Boolean aBoolean) {

                                                        // refreshToken timeout
                                                        Observable.timer(getTimeOut(expiration), TimeUnit.SECONDS)
                                                                .flatMap( new Func1<Long, Observable<Long>>() {
                                                                    @Override
                                                                    public Observable<Long> call(Long aLong) {
                                                                        return Exp.refreshToken()
                                                                                .flatMap(new Func1<Auth, Observable<Long>>() {
                                                                                    @Override
                                                                                    public Observable<Long> call(Auth auth) {
                                                                                        AppSingleton.getInstance().setToken(auth.getToken());
                                                                                        AppSingleton.getInstance().setAuth(auth);
                                                                                        socketManager.refreshConnection();
                                                                                        //callback listen for authConnection
                                                                                        if (Exp.authConnection.containsKey(Utils.UPDATE)) {
                                                                                            Subscriber subscriber = authConnection.get(Utils.UPDATE);
                                                                                            subscriber.onNext(true);
                                                                                            subscriber.onCompleted();
                                                                                        }
                                                                                        return refreshTokenAuth(auth);
                                                                                    }
                                                                                });
                                                                    }
                                                                }).subscribeOn(Schedulers.newThread())
                                                                .observeOn(Schedulers.newThread())
                                                                .subscribe();
                                                        socketManager = new SocketManager();
                                                        Observable<Boolean> booleanObservable = Observable.just(true);
                                                        if (enableSocket) {
                                                            booleanObservable = socketManager.startSocket();
                                                        }
                                                        return booleanObservable;
                                                    }
                                                });
                                    }
                                })
                                .doOnError(new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Log.d(LOG_TAG, "EXP ERROR " + throwable.getLocalizedMessage());
                                        //callback listen for authConnection
                                        if (Exp.authConnection.containsKey(Utils.ERROR)) {
                                            Subscriber subscriber = authConnection.get(Utils.ERROR);
                                            subscriber.onNext(true);
                                            subscriber.onCompleted();
                                        }
                                    }
                                });
                    }
                });
    }


    /**
     * Start SDK with Auth
     * @param auth
     * @return
     */
    public static Observable<Boolean> start_auth(final Auth auth){
        return ExpService.init(AppSingleton.getInstance().getHost())
                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean result) {
                        Log.d(LOG_TAG, "EXP login response :" + auth.getToken());
                        AppSingleton.getInstance().setToken(auth.getToken());
                        AppSingleton.getInstance().setHostSocket(AppSingleton.getInstance().getHost());
                        AppSingleton.getInstance().setAuth(auth);
                        final BigInteger expiration = auth.getExpiration();
                        return ExpService.init(AppSingleton.getInstance().getHost(), auth.getToken())
                                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                                    @Override
                                    public Observable call(Boolean aBoolean) {

                                        // refreshToken timeout
                                        Observable.timer(getTimeOut(expiration), TimeUnit.SECONDS)
                                                .flatMap( new Func1<Long, Observable<Long>>() {
                                                    @Override
                                                    public Observable<Long> call(Long aLong) {
                                                        return Exp.refreshToken()
                                                                .flatMap(new Func1<Auth, Observable<Long>>() {
                                                                    @Override
                                                                    public Observable<Long> call(Auth auth) {
                                                                        AppSingleton.getInstance().setToken(auth.getToken());
                                                                        AppSingleton.getInstance().setAuth(auth);
                                                                        socketManager.refreshConnection();
                                                                        //callback listen for authConnection
                                                                        if (Exp.authConnection.containsKey(Utils.UPDATE)) {
                                                                            Subscriber subscriber = authConnection.get(Utils.UPDATE);
                                                                            subscriber.onNext(true);
                                                                            subscriber.onCompleted();
                                                                        }
                                                                        return refreshTokenAuth(auth);
                                                                    }
                                                                });
                                                    }
                                                }).subscribeOn(Schedulers.newThread())
                                                .observeOn(Schedulers.newThread())
                                                .subscribe();
                                        socketManager = new SocketManager();
                                        Observable<Boolean> booleanObservable = Observable.just(true);
                                        if (enableSocket) {
                                            booleanObservable = socketManager.startSocket();
                                        }
                                        return booleanObservable;
                                    }
                                })
                                .doOnError(new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Log.d(LOG_TAG, "EXP ERROR " + throwable.getLocalizedMessage());
                                        //callback listen for authConnection
                                        if (Exp.authConnection.containsKey(Utils.ERROR)) {
                                            Subscriber subscriber = authConnection.get(Utils.ERROR);
                                            subscriber.onNext(true);
                                            subscriber.onCompleted();
                                        }
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

    /**
     * Get Timout in seconds
     * @param expiration
     * @return
     */
    public static int getTimeOut(BigInteger expiration) {
        Date currentDate = new Date();
        return  (int) (( expiration.longValue() - currentDate.getTime() ) / 1000);
    }

    /**
     * Refresh Token with timer in seconds
     * @param auth
     * @return
     */
    private static Observable<Long> refreshTokenAuth(Auth auth){
        // refreshToken timeout
        Observable<Long> observableRefresh = (Observable<Long>) Observable.timer(getTimeOut(auth.getExpiration()), TimeUnit.SECONDS).flatMap(new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                return Exp.refreshToken()
                        .flatMap(new Func1<Auth, Observable<Long>>() {
                            @Override
                            public Observable<Long> call(Auth auth) {
                                AppSingleton.getInstance().setToken(auth.getToken());
                                AppSingleton.getInstance().setAuth(auth);
                                socketManager.refreshConnection();
                                //callback listen for authConnection
                                if(Exp.authConnection.containsKey(Utils.UPDATE)){
                                    Subscriber subscriber = authConnection.get(Utils.UPDATE);
                                    subscriber.onNext(true);
                                    subscriber.onCompleted();
                                }
                                return refreshTokenAuth(auth);
                            }
                        }).doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.d(LOG_TAG, "EXP ERROR " + throwable.getLocalizedMessage());
                                //callback listen for authConnection
                                if (Exp.authConnection.containsKey(Utils.ERROR)) {
                                    Subscriber subscriber = authConnection.get(Utils.ERROR);
                                    subscriber.onNext(true);
                                    subscriber.onCompleted();
                                }
                            }
                        });
            }
        });

        return observableRefresh;
    }

    /**
     * Init with host and token
     * @param host
     * @param token
     * @return
     */
    public Observable<Boolean> init(String host, String token){
        return ExpService.init(host,token);
    }
}

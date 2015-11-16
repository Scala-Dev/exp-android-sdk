package com.scala.expandroidsdk;

import com.scala.expandroidsdk.model.Token;
import com.scala.expandroidsdk.observer.ExpObservable;

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
    public static Observable start(String host, String uuid, String secret){
        Map<String,Object> header = new HashMap<String,Object>();
        header.put(Utils.TYP, Utils.JWT);
        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put(Utils.UUID, uuid);
        String token = Jwts.builder().setHeader(header).setClaims(payload).signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
        AppSingleton.getInstance().setToken(token);
        return  ExpService.init(host,token);
    }

    /**
     * Start with user credentials TODO make this ExpObservable
     * @param host
     * @param user
     * @param password
     * @param organization
     * @return
     */
    public static Observable   start(final String host, String user, String password, String organization){
        final Map<String,String> options = new HashMap<String,String>();
        options.put(Utils.USERNAME,user);
        options.put(Utils.PASSWORD,password);
        options.put(Utils.ORG, organization);
        return ExpService.init(host)
                .flatMap(new Func1<Boolean, Observable>() {
                    @Override
                    public Observable call(Boolean result) {
                        return Exp.login(options)
                                .flatMap(new Func1<Token, Observable>() {
                                    @Override
                                    public Observable call(Token token) {
                                        AppSingleton.getInstance().setToken(token.getToken());
                                        AppSingleton.getInstance().setHost(host);
                                        return ExpService.init(host, token.getToken())
                                                .flatMap(new Func1<Boolean, Observable>() {
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



}

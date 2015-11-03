package com.scala.expandroidsdk;

import android.util.Log;

import com.scala.expandroidsdk.adapters.ExpObservable;
import com.scala.expandroidsdk.model.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar Oyarzun on 11/2/15.
 */
public class Runtime {

    public static final String TYP = "typ";
    public static final String JWT = "JWT";
    public static final String UUID = "uuid";

    public ExpObservable start(String host,String uuid,String secret){
        Map<String,Object> header = new HashMap<String,Object>();
        header.put(TYP, JWT);
        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put(UUID, uuid);
        String token = Jwts.builder().setHeader(header).setClaims(payload).signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
        AppSingleton.getInstance().setToken(token);
        return  ExpService.init(host);
    }

    public Observable start(String host, String user, String password, String organization){
        Map<String,String> options = new HashMap<String,String>();
        options.put("username",user);
        options.put("password",password);
        options.put("org", organization);
        ExpService.init(host);
        return Exp.login(options).map(new Func1<Token, Observable<Token>>() {
            @Override
            public Observable<Token> call(Token token) {
                Log.e("Response", token.toString());
                AppSingleton.getInstance().setToken(token.getToken());
                return null;
            }
        });


    }



}

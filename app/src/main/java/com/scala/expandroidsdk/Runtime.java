package com.scala.expandroidsdk;

import com.scala.expandroidsdk.model.Token;

import java.util.HashMap;
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
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ORG = "org";

    /**
     * Start with device credentials Host,UUID,Secret TODO make this ExpObservable
     * @param host
     * @param uuid
     * @param secret
     * @return
     */
    public Observable start(String host,String uuid,String secret){
        Map<String,Object> header = new HashMap<String,Object>();
        header.put(TYP, JWT);
        Map<String,Object> payload = new HashMap<String,Object>();
        payload.put(UUID, uuid);
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
    public Observable start(final String host, String user, String password, String organization){
        final Map<String,String> options = new HashMap<String,String>();
        options.put(USERNAME,user);
        options.put(PASSWORD,password);
        options.put(ORG, organization);
        return ExpService.init(host)
                .flatMap(new Func1<Boolean, Observable>() {
                    @Override
                    public Observable call(Boolean result) {
                        return Exp.login(options)
                                .map(new Func1<Token,Observable>() {
                                    @Override
                                    public Observable call(Token token) {
                                        return ExpService.init(host,token.getToken());
                                    }
                                });
                    }
                });
    }



}

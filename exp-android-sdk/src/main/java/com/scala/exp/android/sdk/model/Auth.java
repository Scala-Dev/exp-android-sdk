package com.scala.exp.android.sdk.model;

import java.math.BigInteger;

/**
 * Created by Cesar Oyarzun on 1/4/16.
 */
public class Auth {
    private String token;
    private BigInteger expiration;
    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Expiration Token
     * @return
     */
    public BigInteger getExpiration() {
        return expiration;
    }

    /**
     * Expirtation Token
     * @param expiration
     */
    public void setExpiration(BigInteger expiration) {
        this.expiration = expiration;
    }
}

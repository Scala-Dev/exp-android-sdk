package com.scala.exp.android.sdk.model;

import java.math.BigInteger;

/**
 * Created by Cesar Oyarzun on 1/4/16.
 */
public class Auth {

    private String restrictedToken;
    private String token;
    private BigInteger expiration;
    private Identity identity;
    private Network network;

    /**
     *
     * @return The restrictedToken
     */
    public String getRestrictedToken() {
        return restrictedToken;
    }

    /**
     * Sets the restrictedToken (used for read only operations)
     * @param restrictedToken
     */
    public void setRestrictedToken(String restrictedToken) {
        this.restrictedToken = restrictedToken;
    }

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

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }
}

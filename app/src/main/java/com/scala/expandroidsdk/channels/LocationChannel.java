package com.scala.expandroidsdk.channels;


import io.socket.client.Socket;

/**
 * Created by Cesar Oyarzun on 11/4/15.
 */
public class LocationChannel extends AbstractChannel {

    public static final String CHANNEL = "location";
    public LocationChannel(Socket socket){
        this.socket = socket;
        setChannel(CHANNEL);
    }


}

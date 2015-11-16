package com.scala.exp.android.sdk.channels;


import io.socket.client.Socket;

/**
 * Created by Cesar Oyarzun on 11/4/15.
 */
public class SystemChannel extends AbstractChannel {

    public static final String CHANNEL = "system";
    public SystemChannel(Socket socket){
        this.socket = socket;
        setChannel(CHANNEL);
    }


}

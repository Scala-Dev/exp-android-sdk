package com.scala.exp.android.sdk.channels;


import io.socket.client.Socket;

/**
 * Created by Cesar Oyarzun on 11/4/15.
 */
public class CommonChannel extends AbstractChannel {


    public CommonChannel(Socket socket,String channelName){
        this.socket = socket;
        setChannel(channelName);
    }
}

package com.scala.exp.android.sdk.channels;


import com.scala.exp.android.sdk.SocketManager;

/**
 * Created by Cesar Oyarzun on 11/4/15.
 */
public class Channel extends AbstractChannel {

    public Channel(SocketManager socketManager, String channelName,int system,int consumerApp){
        this.socketManager = socketManager;
        setChannel(channelName);
        setSystem(system);
        setConsumerApp(consumerApp);
    }
}

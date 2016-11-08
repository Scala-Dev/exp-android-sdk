package com.scala.exp.android.sdk.channels;

import com.scala.exp.android.sdk.SocketManager;

/**
 * Created by Cesar Oyarzun on 2/8/16.
 */
public class ChannelFactory {

    public static Channel createChannel(String channelName, SocketManager socketManager, boolean system, boolean consumerApp){
        return new Channel(socketManager,channelName,system,consumerApp);
    }
}

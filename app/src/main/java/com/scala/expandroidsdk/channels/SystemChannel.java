package com.scala.expandroidsdk.channels;


import com.scala.expandroidsdk.Utils;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import io.socket.client.Socket;
import rx.Subscriber;

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

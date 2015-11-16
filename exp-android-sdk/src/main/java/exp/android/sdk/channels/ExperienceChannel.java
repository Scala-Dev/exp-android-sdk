package exp.android.sdk.channels;


import io.socket.client.Socket;

/**
 * Created by Cesar Oyarzun on 11/4/15.
 */
public class ExperienceChannel extends AbstractChannel {

    public static final String CHANNEL = "experience";
    public ExperienceChannel(Socket socket){
        this.socket = socket;
        setChannel(CHANNEL);
    }
}

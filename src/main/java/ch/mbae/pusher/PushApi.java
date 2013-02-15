package ch.mbae.pusher;

import java.util.Collection;

/**
 * Author: wge
 * Date: 09/02/2013
 * Time: 18:55
 */

public interface PushApi
{
    /**
     * The implementation must keep these secret.
     *
     * @param pusherApplicationId
     * @param pusherApplicationKey
     * @param pusherApplicationSecret
     */
    void setGAECredentials(String pusherApplicationId, String pusherApplicationKey, String pusherApplicationSecret);

    /**
     *
     * @param channelName the particular channel
     * @param eventName anything that describes your event.
     * @param jsonData data to be pushed
     * @param socketId
     * @return A response object encapsulating all the headers from PusherApp.com
     * @throws PusherTransportException
     */
    PusherResponse pushEvent( String channelName, String eventName, String jsonData, String socketId) throws PusherTransportException;

    /**
     * Should be called when we have finished using a channel.
     * For example, a disconnection has occurred.
     * @param channelName
     */
    void disposeOfChannel(String channelName);

    /**
     * Show all the channels currently being used
     * @return
     */
    Collection<String> listLiveChannels();
}

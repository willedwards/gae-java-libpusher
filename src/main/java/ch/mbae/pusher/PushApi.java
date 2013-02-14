package ch.mbae.pusher;

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


}

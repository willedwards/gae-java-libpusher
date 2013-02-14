package ch.mbae.pusher;/**
 * Author: wge
 * Date: 14/02/2013
 * Time: 22:17
 */

import ch.mbae.pusher.transport.GAEPusherTransport;
import ch.mbae.pusher.transport.HttpClientPusherTransport;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

class PushImpl implements PushApi
{
    private static final Logger log = Logger.getLogger(PushImpl.class);

    @Override
    public void setGAECredentials(String pusherApplicationId, String pusherApplicationKey, String pusherApplicationSecret)
    {
    }

    @Override
    public PusherResponse pushEvent(String channelName, String eventName, String jsonData, String socketId) throws PusherTransportException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private ConcurrentHashMap<String,PusherChannel> channelMap = new ConcurrentHashMap<String,PusherChannel>();

    private PusherChannel findOrCreateHttpChannel(String channelName)
    {
        PusherChannel channel = channelMap.putIfAbsent(channelName,buildHttpChannel(channelName));
        return channel;
    }

    private PusherChannel findOrCreateGAEChannel(String channelName)
    {
        PusherChannel channel = channelMap.putIfAbsent(channelName,buildGAEChannel(channelName));
        return channel;
    }

    private PusherChannel buildGAEChannel(String channelName)
    {
        return new PusherChannel(channelName,new GAEPusherTransport());

    }
    private PusherChannel buildHttpChannel(String channelName)
    {
        return new PusherChannel(channelName,new HttpClientPusherTransport());
    }
}

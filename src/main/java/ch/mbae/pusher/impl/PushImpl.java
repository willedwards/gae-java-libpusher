package ch.mbae.pusher.impl;/**
 * Author: wge
 * Date: 14/02/2013
 * Time: 22:17
 */

import ch.mbae.pusher.*;
import ch.mbae.pusher.transport.HttpClientPusherTransport;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class PushImpl implements PushApi
{
    private static final Logger log = Logger.getLogger(PushImpl.class);
    private ConcurrentHashMap<String,PusherChannel> channelMap = new ConcurrentHashMap<String,PusherChannel>();

    @Override
    public void setGAECredentials(String pusherApplicationId, String pusherApplicationKey, String pusherApplicationSecret)
    {
        CredentialHolder.build(pusherApplicationId,pusherApplicationKey,pusherApplicationSecret);
    }

    @Override
    public PusherResponse pushEvent(String channelName, String eventName, String jsonData, String socketId) throws PusherTransportException
    {
        PusherChannel channel = findOrCreateHttpChannel(channelName);

        return  channel.pushEvent(eventName,  jsonData,  socketId);

    }

    @Override
    public void disposeOfChannel(String channelName)
    {
        PusherChannel channel = channelMap.get(channelName);

        if(channel != null)
        {
            channelMap.remove(channelName);
            log.info("channel " + channelName + " successfully removed ");
        }
        else
        {
            log.warn("channel " + channelName + " not found ");
        }
    }

    @Override
    public Collection<String> listLiveChannels()
    {
        return channelMap.keySet();
    }

    private PusherChannel findOrCreateHttpChannel(String channelName)
    {
        PusherChannel channel = channelMap.get(channelName);
        if(channel == null)
        {
            channel = new PusherChannel(channelName,new HttpClientPusherTransport());
            channelMap.putIfAbsent(channelName,channel);
        }
        return channel;
    }


}

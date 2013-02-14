/**
 * Author: marcbaechinger
 * Copyright 2011. Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package ch.mbae.pusher;

import ch.mbae.pusher.util.PusherUtil;
import java.net.URL;

/**
 * 
 * @author marcbaechinger
 */
public class PusherChannel {
    
    private PusherTransport transport;
    private String channelName;

    public PusherChannel(String channelName,  PusherTransport transport) {
        this.channelName = channelName;
        this.transport = transport;
    }
    
    /**
     * Delivers a message to the Pusher API without providing a socket_id
     * @param event
     * @param jsonData
     * @return
     */
    public PusherResponse pushEvent(String event, String jsonData) throws PusherTransportException{
    	return pushEvent(event, jsonData, "");
    }
    
    /**
     * Delivers a message to the Pusher API
     * @param event
     * @param jsonData
     * @param socketId
     * @return
     */
    public PusherResponse pushEvent(String event, String jsonData, String socketId) throws PusherTransportException{
    	//Build URI path
    	String uriPath = PusherUtil.buildURIPath(this.channelName, PusherCredentials.APPLICATION_ID);
    	//Build query
    	String query = PusherUtil.buildQuery(event, jsonData, socketId, PusherCredentials.APPLICATION_KEY);
    	//Generate signature
    	String signature = PusherUtil.buildAuthenticationSignature(uriPath, query, PusherCredentials.APPLICATION_SECRET);
    	//Build URI
    	URL url = PusherUtil.buildURI(uriPath, query, signature);
        
        return this.transport.fetch(url, jsonData);
    }
}

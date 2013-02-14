/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.pusher;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.easymock.EasyMock.*;

/**
 *
 * @author marcbaechinger
 */
public class PusherChannelTest implements PusherCredentials {
    
    private static final Logger LOGGER = Logger.getLogger(PusherChannelTest.class); 
    
    private static final String JSON_STRING = "{ originator: " + PusherChannelTest.class.getName() + "}";
    
    private PusherChannel testee;
    private PusherTransport transportMock;
            
    @Before
    public void setUp() {
        // create a mock for the transport
        this.transportMock = createMock(PusherTransport.class);
        // create the channel
        this.testee = new PusherChannel(CHANNEL, transportMock);
    }
    
    
    /**
     * Test o f pushEvent method, of class PusherChannel.
     */
    @Test
    public void testPushEvent_String_String() throws PusherTransportException {
        Capture<URL> url = new Capture<URL>();
        Capture<String> json = new Capture<String>();
        
        // expect the transport to be called with a given URL and a json string
        expect(this.transportMock.fetch(capture(url), capture(json))).andReturn((PusherResponse)null);
        // start replay the mock after definition of expectations
        replay(this.transportMock);
        
        // now trigger the event
        this.testee.pushEvent(EVENT, JSON_STRING );
        
        LOGGER.debug(json.getValue());
        
        // verify captured values
        Assert.assertEquals(JSON_STRING, json.getValue());
        
        // verify if expectations are met
        verify(this.transportMock);
        
    }
    
}

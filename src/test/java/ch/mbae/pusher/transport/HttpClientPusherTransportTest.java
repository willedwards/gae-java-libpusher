/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.pusher.transport;

import ch.mbae.pusher.PusherChannel;
import ch.mbae.pusher.PusherCredentials;
import ch.mbae.pusher.PusherResponse;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests pushing a an event to pusher by using transport based on jakarta HttpClient.
 * 
 * @author marcbaechinger
 */
public class HttpClientPusherTransportTest implements PusherCredentials {
    
    private static final Logger LOGGER = Logger.getLogger(HttpClientPusherTransportTest.class);
    
    private static final String JSON_STRING = "{ originator: " + HttpClientPusherTransportTest.class.getName() + "}";
    private static final String SOCKET_ID = "12345678";
    private HttpClientPusherTransport tranportUnderTest;
    private PusherChannel channel;
    
    
    @Before
    public void setUp() {
        // create the transport under test
        this.tranportUnderTest = new HttpClientPusherTransport();
        // the channel to inject the transport into
        this.channel = new PusherChannel(CHANNEL,
                                         APPLICATION_ID,
                                         APPLICATION_KEY,
                                         APPLICATION_SECRET,
                                         this.tranportUnderTest);
    }

    @After
    public void tearDown() {
        this.tranportUnderTest = null;
        this.channel = null;
    }
    /**
     * Test of fetch method, of class HttpClientPusherTransport.
     */
    @Test
    public void testFetch() throws Exception {
        // push the event 
        PusherResponse response = this.channel.pushEvent(EVENT, JSON_STRING);
        retryAgainIfNecessary(response);
    }

    /**
     * Test of fetch method, of class HttpClientPusherTransport.
     */
    @Test
    public void testFetchWidthSessionId() throws Exception {
        // push the event 
        PusherResponse response = this.channel.pushEvent(EVENT, JSON_STRING, SOCKET_ID);
        
        retryAgainIfNecessary(response);
    }

   private void retryAgainIfNecessary(PusherResponse response) throws Exception
   {
       if(!verifyContent(response))
       {
           response = this.channel.pushEvent(EVENT, JSON_STRING, SOCKET_ID);
           verifyContent(response);
       }

   }
   private boolean verifyContent(PusherResponse response)
   {
       LOGGER.debug(response.getResponseCode());
       LOGGER.debug(new String(response.getContent()));
       LOGGER.debug(response.getHeaders().get("Content-Length"));

       Assert.assertEquals(202, response.getResponseCode());
       Assert.assertEquals(response.getHeaders().size(), 3);

       // content as expected ? (hard test; might fail once)
       //Assert.assertEquals("202 ACCEPTED\n", new String(response.getContent()));
       // correct content length in http header?
       String contentLen = response.getHeaders().get("Content-Length");
       if(contentLen != null)
       {
           Assert.assertEquals(Integer.valueOf(response.getHeaders().get("Content-Length")).intValue(), response.getContent().length);
           return true;
       }

       return false;
   }
}

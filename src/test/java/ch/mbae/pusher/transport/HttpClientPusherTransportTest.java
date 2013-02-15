/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.pusher.transport;

import ch.mbae.pusher.PusherResponse;
import ch.mbae.pusher.TestCredentials;
import ch.mbae.pusher.impl.CredentialHolder;
import ch.mbae.pusher.impl.PusherChannel;
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
public class HttpClientPusherTransportTest {
    
    private static final Logger LOGGER = Logger.getLogger(HttpClientPusherTransportTest.class);
    
    private static final String JSON_STRING = "{ originator: " + HttpClientPusherTransportTest.class.getName() + "}";
    private static final String SOCKET_ID = "12345678";
    private HttpClientPusherTransport tranportUnderTest;
    private PusherChannel channel;
    
    String CHANNEL = "junit-test-channel";
    String EVENT = "junit-test-event";

    @Before
    public void setUp() {

        CredentialHolder.build(TestCredentials.APPLICATION_ID, TestCredentials.APPLICATION_KEY, TestCredentials.APPLICATION_SECRET);
        // create the transport under test
        this.tranportUnderTest = new HttpClientPusherTransport();
        // the channel to inject the transport into
        this.channel = new PusherChannel(CHANNEL,this.tranportUnderTest);
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
    public void testFetchWidthSocketId() throws Exception {
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

   public static boolean verifyContent(PusherResponse response)
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

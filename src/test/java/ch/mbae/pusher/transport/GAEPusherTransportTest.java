/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.pusher.transport;

import ch.mbae.pusher.impl.PusherChannel;
import ch.mbae.pusher.PusherResponse;
import ch.mbae.pusher.PusherTransportException;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author marcbaechinger
 */
public class GAEPusherTransportTest  {
    private PusherChannel channel;
    private static final Logger log = Logger.getLogger(GAEPusherTransportTest.class);

    @Before
    public void setUp() {
        this.channel = new PusherChannel("gae",new GAEPusherTransport());
    }
    

    /**
     * Test of fetch method, of class GAEPusherTransport.
     */
    @Test
    @Ignore
    public void testFetch()
    {
        try
        {
            PusherResponse response = channel.pushEvent("gae-event", "{'transport': 'GAE'}");
            int x = 2;
        }
        catch (PusherTransportException e)
        {
            log.error(e.getMessage());
        }
    }
}

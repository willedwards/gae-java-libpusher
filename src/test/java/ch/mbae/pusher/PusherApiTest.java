/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.pusher;

import ch.mbae.pusher.impl.PushImpl;
import ch.mbae.pusher.transport.HttpClientPusherTransportTest;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 *
 * @author William Edwards
 */
public class PusherApiTest
{
    
    private static final Logger LOGGER = Logger.getLogger(PusherApiTest.class);
    
    private static final String JSON_STRING = "{ originator: " + PusherApiTest.class.getName() + "}";
    
    private PushApi api;

    @Before
    public void setUp() {
        api = new PushImpl();
    }
    
    @Test
    public void pushTest()
    {
        api.setGAECredentials(TestCredentials.APPLICATION_ID,TestCredentials.APPLICATION_KEY,TestCredentials.APPLICATION_SECRET);

        try
        {
            PusherResponse response = api.pushEvent("company-001","create",JSON_STRING,"987");

            assert(response != null);

            HttpClientPusherTransportTest.verifyContent(response);


            Collection<String> existingChannels = api.listLiveChannels();

            assert(existingChannels.size() == 1);

            existingChannels.iterator().next().equals("company-001");

            api.disposeOfChannel("company-001");

            assert(api.listLiveChannels().size() == 0);

        }
        catch (PusherTransportException e)
        {
            LOGGER.error(e.getMessage());
        }
    }


}

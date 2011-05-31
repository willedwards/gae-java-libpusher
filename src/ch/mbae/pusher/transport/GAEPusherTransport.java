package ch.mbae.pusher.transport;

import ch.mbae.pusher.PusherResponse;
import ch.mbae.pusher.PusherTransport;
import java.net.URL;

/**
 * Transport implementation for GAE.
 * 
 * @author marcbaechinger
 */
public class GAEPusherTransport implements PusherTransport {

    @Override
    public PusherResponse fetch(URL url, String jsonData) {
        //Create Google APP Engine Fetch URL service and request
        URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
        HTTPRequest request = new HTTPRequest(url, HTTPMethod.POST);
        request.addHeader(new HTTPHeader("Content-Type", "application/json"));
        request.setPayload(jsonData.getBytes());

        //Start request
        try {
                // TODO map GAE to PusherRequest
            throw new IllegalStateException("not implemented yet. See TODO in source code");
                //return urlFetchService.fetch(request);
        } catch (IOException e) {
                //Log warning
                e.printStackTrace();
                return null;
        }
    }
    
}

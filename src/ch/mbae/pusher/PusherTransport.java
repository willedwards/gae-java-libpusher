/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.pusher;

import java.net.URL;

/**
 *
 * @author marcbaechinger
 */
public interface PusherTransport {
    PusherResponse fetch(URL url, String jsonData);
}

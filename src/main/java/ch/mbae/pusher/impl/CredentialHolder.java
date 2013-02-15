package ch.mbae.pusher.impl;/**
 * Author: wge
 * Date: 15/02/2013
 * Time: 12:54
 */

public class CredentialHolder
{
    private static final CredentialHolder instance = new CredentialHolder();

    private  String APPLICATION_ID;
    private  String APPLICATION_KEY;
    private  String APPLICATION_SECRET;

    public static String getAPPLICATION_ID()
    {
        return instance.APPLICATION_ID;
    }

    public static String getAPPLICATION_KEY()
    {
        return instance.APPLICATION_KEY;
    }

    public static String getAPPLICATION_SECRET()
    {
        return instance.APPLICATION_SECRET;
    }

    public static synchronized void build (String application_id, String application_key, String application_secret)
    {
        instance.APPLICATION_ID = application_id;
        instance.APPLICATION_KEY = application_key;
        instance.APPLICATION_SECRET = application_secret;
    }


}

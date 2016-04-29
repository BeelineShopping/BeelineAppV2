package com.beelineshopping.beelineandroidapp;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;

/**
 * Created by Shelby on 2/17/2016.
 */
public class HttpHandler {
    private static OkHttpClient client = null;
    private static String credentials = "";

    protected HttpHandler() {

    }

    public static OkHttpClient getInstance() {
        if(client == null) {
            client = new OkHttpClient();
        }
        return client;
    }

    public static void setCredentials(String userName, String password) {
        credentials = Credentials.basic(userName, password);
    }

    public static String getCredentials() {
        return credentials;
    }
}

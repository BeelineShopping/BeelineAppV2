package com.beelineshopping.beelineandroidapp.tasks;

/**
 * Created by Shelby on 2/17/2016.
 */
import android.os.AsyncTask;

import com.beelineshopping.beelineandroidapp.HttpHandler;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRequestTask extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... params) {
        String uri = params[0];
        OkHttpClient client = HttpHandler.getInstance();
        String creds = HttpHandler.getCredentials();
        Request request = new Request.Builder().url(uri).header("User-Agent", "OkHttp Headers.java").addHeader("Accept", "application/json; q=0.5").addHeader("Accept", "application/vnd.github.v3+json").
        addHeader("Authorization", creds).build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();
            } else {
                int error_r = response.code();
                return "ERROR";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

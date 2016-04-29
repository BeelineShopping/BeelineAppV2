package com.beelineshopping.beelineandroidapp;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Shelby on 1/27/2016.
 */
public class myAsyncTask extends AsyncTask<String, String, String> {
    OkHttpClient client = new OkHttpClient();

    String my_run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    @Override
    protected String doInBackground(String... params) {
        String my_url = params[0];
        try {
            return my_run(my_url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

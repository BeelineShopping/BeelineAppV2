package com.beelineshopping.beelineandroidapp.tasks;

/**
 * Created by Shelby on 1/27/2016.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;





import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


    public class GetRequestLoadTask extends AsyncTask<String, String, String>{
        OkHttpClient client = new OkHttpClient();


        public String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        protected String doInBackground(String... params) {
           String url = params[0];
            try {
                run(url);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

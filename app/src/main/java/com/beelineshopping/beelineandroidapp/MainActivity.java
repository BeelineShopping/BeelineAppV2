package com.beelineshopping.beelineandroidapp;

import android.content.ContentValues;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.beelineshopping.beelineandroidapp.tasks.GetRequestTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Context context;

    String testStr;
    BeelineDbHelper mDbHelper = new BeelineDbHelper(this);
    DatabaseUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Data loaded into DB
        boolean load = true;

        context = getApplicationContext();

        //HttpHandler.setCredentials("test@example.com", "password");

        if (isNetworkAvailable()) {

            //Load data from endpoints ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            //Load Lists =====================================================================
            //http requests
            GetRequestTask listTask = new GetRequestTask();
            listTask.execute("https://beeline-db.herokuapp.com/api/v1/lists");
            //DB setup
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            long newRowId;
            String name;
            String section;
            String aisle;
            String store;
            //===================================================
            //handle/format json response body
            String list_title;
            JSONObject list_titleObj;
            try {
                String response = listTask.get();
                if (response.equals("ERROR")) {
                    load = false;
                }
                //User is logged in, start loading data----------------------------------
                //empty tables.
                db.delete(BeelineContract.ShoppingListDetails.TABLE_NAME, null, null);
                db.delete(BeelineContract.UserDetails.TABLE_NAME, null, null);

                JSONObject json = new JSONObject(response);
                JSONArray shopping_lists = json.getJSONArray("shopping_lists");
                int shopSize = shopping_lists.length();
                for (int i = 0; i < shopSize; i++) {
                    list_titleObj = shopping_lists.getJSONObject(i);
                    list_title = list_titleObj.getString("list_title");

                    JSONObject itemsObj2 = shopping_lists.getJSONObject(i);
                    JSONArray items = itemsObj2.getJSONArray("items");
                    int size = items.length();
                    for (int j = 0; j < size; j++) {
                        JSONObject itemObj = items.getJSONObject(j);
                        name = itemObj.getString("name");
                        section = itemObj.getString("section");
                        aisle = itemObj.getString("aisle");
                        store = itemObj.getString("store");
                        //insert set of list details
                        values.put(BeelineContract.ShoppingListDetails.COLUMN_NAME_LIST_TITLE, list_title);
                        values.put(BeelineContract.ShoppingListDetails.COLUMN_NAME_NAME, name);
                        values.put(BeelineContract.ShoppingListDetails.COLUMN_NAME_SECTION, section);
                        values.put(BeelineContract.ShoppingListDetails.COLUMN_NAME_AISLE, aisle);
                        values.put(BeelineContract.ShoppingListDetails.COLUMN_NAME_STORE, store);
                        newRowId = db.insert(
                                BeelineContract.ShoppingListDetails.TABLE_NAME,
                                BeelineContract.ShoppingListDetails.COLUMN_NAME_NULLABLE,
                                values);

                        db = mDbHelper.getReadableDatabase();

                        // Read data
                        String[] projection = {
                                BeelineContract.ShoppingListDetails._ID,
                                BeelineContract.ShoppingListDetails.COLUMN_NAME_LIST_TITLE,
                                BeelineContract.ShoppingListDetails.COLUMN_NAME_NAME,
                                BeelineContract.ShoppingListDetails.COLUMN_NAME_SECTION,
                                BeelineContract.ShoppingListDetails.COLUMN_NAME_AISLE,
                                BeelineContract.ShoppingListDetails.COLUMN_NAME_STORE
                        };

//        String sortOrder =
//                BeelineContract.ShoppingListDetails.COLUMN_NAME_UPDATED + " DESC";

                        Cursor c = db.query(
                                BeelineContract.ShoppingListDetails.TABLE_NAME,  // The table to query
                                null,                               // The columns to return
                                null,                                // The columns for the WHERE clause
                                null,                            // The values for the WHERE clause
                                null,                                     // don't group the rows
                                null,                                     // don't filter by row groups
                                null                                 // The sort order
                        );

                        String crStr = dbUtils.dumpCursorToString(c);
                        c.close();


//        if (c.moveToFirst()) { //data?
//            String title = c.getString(c.getColumnIndex("title"));//show that data is accessible
//            System.out.print("title");
//        }


                    }
                }

            } catch (InterruptedException | ExecutionException | JSONException e) {
                e.printStackTrace();
            }

            //Load user data=====================================================
            //http request
            GetRequestTask userTask = new GetRequestTask();
            userTask.execute("https://beeline-db.herokuapp.com/api/v1/user");
            try {
                String response = userTask.get();
                if (response.equals("ERROR")) {
                    load = false;
                }
                //format data from http response
                JSONObject userObj = new JSONObject(response);
                String user_id = userObj.getString("id");
                String email = userObj.getString("email");
                String username = userObj.getString("username");
                String first_name = userObj.getString("first_name");
                String last_name = userObj.getString("last_name");
                String created_at = userObj.getString("created_at");
                String updated_at = userObj.getString("updated_at");

                //insert data into DB
                db = mDbHelper.getWritableDatabase();
                ContentValues user_values = new ContentValues();
                user_values.put(BeelineContract.UserDetails.COLUMN_NAME_USER_ID, user_id);
                user_values.put(BeelineContract.UserDetails.COLUMN_NAME_EMAIL, email);
                user_values.put(BeelineContract.UserDetails.COLUMN_NAME_USERNAME, username);
                user_values.put(BeelineContract.UserDetails.COLUMN_NAME_FIRST_NAME, first_name);
                user_values.put(BeelineContract.UserDetails.COLUMN_NAME_LAST_NAME, last_name);
                user_values.put(BeelineContract.UserDetails.COLUMN_NAME_CREATED_AT, created_at);
                user_values.put(BeelineContract.UserDetails.COLUMN_NAME_UPDATED_AT, updated_at);

                db.insert(
                        BeelineContract.UserDetails.TABLE_NAME,
                        BeelineContract.UserDetails.COLUMN_NAME_NULLABLE,
                        user_values);

            } catch (InterruptedException | ExecutionException | JSONException e) {
                e.printStackTrace();
            }


            if (load) {
                //Data loaded send user to ListCollectionActivity.
                System.out.println("App data has been loaded, sending user to ListCollectionActivity");
                Intent intent = new Intent(getApplicationContext(), ListCollectionActivity.class);
                startActivity(intent);
                finish();
            } else {
                //Data not loaded send user to login activity
                System.out.println("User Not Logged in, redirect to UserLoginActivity");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
            //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        }else{
            Toast toast = Toast.makeText(context,
                    "No internet connection", Toast.LENGTH_LONG);
            toast.show();
            Intent ni = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(ni);
            finish();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    /** Called when the user clicks the List View button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ItemSearch.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }








}

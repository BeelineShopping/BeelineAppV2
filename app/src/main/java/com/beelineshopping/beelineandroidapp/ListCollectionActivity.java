package com.beelineshopping.beelineandroidapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.beelineshopping.beelineandroidapp.tasks.Ingredient;

import java.util.ArrayList;

public class ListCollectionActivity extends AppCompatActivity {

    private ArrayList<String> shoppingLists;

    private ListView shoppingListsListView;
    private ArrayAdapter<String> shoppingListsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_collection);

        shoppingLists =  new ArrayList<String>();
        fillList();

        shoppingListsListView = (ListView) findViewById(R.id.shoppingLists);
        shoppingListsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, shoppingLists);
        shoppingListsListView.setAdapter(shoppingListsAdapter);

        shoppingListsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                Object item = adapter.getItemAtPosition(position);

                //Toast.makeText(ListCollectionActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
                shopAisles(item.toString());

            }
        });
    }

    public void shopAisles(String listName) {
        Intent intent = new Intent(ListCollectionActivity.this, Aisles.class);
        intent.putExtra("listName", listName);

        startActivity(intent);
    }

    public void fillList()
    {
        BeelineDbHelper mDbHelper = new BeelineDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT DISTINCT list_title FROM ShoppingList";

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext())
        {
            shoppingLists.add(c.getString(0));
        }
        c.close();
    }
}

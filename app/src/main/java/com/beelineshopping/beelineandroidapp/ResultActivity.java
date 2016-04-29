package com.beelineshopping.beelineandroidapp;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.beelineshopping.beelineandroidapp.cursor_adapters.AisleAdapter;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends ListActivity {

    Context context;

    List<String> listValues;
    AisleAdapter shop_cursor_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        context = getApplicationContext();

        Intent intent = getIntent();
        String search_info = intent.getStringExtra(ItemSearch.EXTRA_MESSAGE);
        String check = "check";


        listValues = new ArrayList<String>();
        listValues.add("one");
        listValues.add("two");
        listValues.add("three");
        listValues.add("four");
        listValues.add("five");

        // initiate the listadapter
        ArrayAdapter<String> myAdapter = new ArrayAdapter <String>(this,
                R.layout.row_layout, R.id.list_item, listValues);
        // assign the list adapter
        setListAdapter(myAdapter);

        //shop_cursor_adapter = new ShopCursorAdapter(this,c,0);


    }


}

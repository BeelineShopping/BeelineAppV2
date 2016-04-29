package com.beelineshopping.beelineandroidapp;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beelineshopping.beelineandroidapp.cursor_adapters.AisleAdapter;
import com.beelineshopping.beelineandroidapp.tasks.Ingredient;

import java.util.ArrayList;

public class Aisle extends AppCompatActivity {
//    private ArrayList<String> shoppingLists;
//    private ArrayAdapter<String> shoppingListsAdapter;
    DatabaseUtils dbUtils;


    AisleAdapter list1Adapter;
    Cursor list1Cursor;
    BeelineDbHelper mDbHelper = new BeelineDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aisle);



//        shoppingLists =  new ArrayList<String>();
//        shoppingLists.add("one");
//        shoppingLists.add("two");

        final ListView list_view1 = (ListView)findViewById(R.id.list1);
//        shoppingListsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, shoppingLists);
//        list_view1.setAdapter(shoppingListsAdapter);
////        final TextView texts = (TextView)findViewById(R.id.item);


        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //AisleAdapter list1Adapter = new AisleAdapter(this,list1Cursor,0);
        db = mDbHelper.getReadableDatabase();
                                Cursor c = db.query(
                                BeelineContract.UserDetails.TABLE_NAME,  // The table to query
                                null,                               // The columns to return
                                null,                                // The columns for the WHERE clause
                                null,                            // The values for the WHERE clause
                                null,                                     // don't group the rows
                                null,                                     // don't filter by row groups
                                null                                 // The sort order
                        );

                        String crStr = dbUtils.dumpCursorToString(c);

        c.moveToFirst();
        AisleAdapter aaList1 = new AisleAdapter(this,c,0);
        list_view1.setAdapter(aaList1);
                        //c.close();
    }
}

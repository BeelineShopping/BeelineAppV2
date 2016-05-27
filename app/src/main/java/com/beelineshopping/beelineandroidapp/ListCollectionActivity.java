package com.beelineshopping.beelineandroidapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import com.beelineshopping.beelineandroidapp.cursor_adapters.AisleAdapter;
import com.beelineshopping.beelineandroidapp.cursor_adapters.ListCollectAdapter;
import com.beelineshopping.beelineandroidapp.tasks.Ingredient;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;


import java.util.ArrayList;

public class ListCollectionActivity extends AppCompatActivity {
    NiftyDialogBuilder materialDesignAnimatedDialog;

    private ArrayList<String> shoppingLists;

    private DatabaseUtils dbUtils;

    private ListView shoppingListsListView;
    private ArrayAdapter<String> shoppingListsAdapter;

    Context mcontext;
    ListCollectAdapter aaList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_collection);
        mcontext = this;

        materialDesignAnimatedDialog = NiftyDialogBuilder.getInstance(this);

        shoppingLists =  new ArrayList<String>();
        //fillList();
//-----------------------------------------------------------------------------
        shoppingListsListView = (ListView) findViewById(R.id.shoppingLists);
        ArrayList<String> mLists = getTitles();
        aaList1 = new ListCollectAdapter(this,mLists);
//        ListCollectAdapter aaList1 = new ListCollectAdapter(this,list_c,0);
        shoppingListsListView.setAdapter(aaList1);
//-------------------------------------------------------------------------------
//        shoppingListsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, shoppingLists);
//        shoppingListsListView.setAdapter(shoppingListsAdapter);

        shoppingListsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                Object item = adapter.getItemAtPosition(position);

                //Toast.makeText(ListCollectionActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
                shopAisles(item.toString());

            }
        });
    }

    public void dialogList(View view){
        DialogPlus dialog = DialogPlus.newDialog(mcontext)
                .setAdapter(aaList1)
                .setOnItemClickListener(new OnItemClickListener(){
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        shopAisles(item.toString());
                    }
                })
                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();
    }

    public void animatedDialog1(View view) {
        materialDesignAnimatedDialog
                .withTitle("Animated Fall Dialog Title")
                .withMessage("Add your dialog message here. Animated dialog description place.")
                .withDialogColor("#1c90ec")
                .withButton1Text("OK")
                .withButton2Text("Cancel")
                .withDuration(700)
                .setCustomView(R.layout.activity_list_collection, view.getContext())
                .withEffect(Effectstype.Fall)
                .show();
    }

    public void shopAisles(String listName) {
        Intent intent = new Intent(ListCollectionActivity.this, AisleSwipe.class);
        intent.putExtra("listName", listName);

        startActivity(intent);
    }

    public void fillList()
    {
        BeelineDbHelper mDbHelper = new BeelineDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT DISTINCT list_title FROM ShoppingList";

        Cursor c = db.rawQuery(query, null);

        String crStr = dbUtils.dumpCursorToString(c);

        while (c.moveToNext())
        {
            shoppingLists.add(c.getString(0));
        }
        c.close();
    }


    public ArrayList<String> getTitles()
    {
        BeelineDbHelper mDbHelper = new BeelineDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT DISTINCT list_title FROM ShoppingList";

        Cursor c = db.rawQuery(query, null);

        ArrayList<String> titles = new ArrayList<String>();
        while (c.moveToNext())
        {
            String title = c.getString( c.getColumnIndex( "list_title") );
            titles.add(title);
        }
        c.close();
        return titles;
    }

    public Cursor getListCursor()
    {
        BeelineDbHelper mDbHelper = new BeelineDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT list_title, _id FROM ShoppingList";

        Cursor c = db.rawQuery(query, null);
        String crStr2 = dbUtils.dumpCursorToString(c);
        ArrayList<String> titles = new ArrayList<String>();
        while (c.moveToNext())
        {
            String title = c.getString( c.getColumnIndex( "list_title") );

            if(!titles.contains(title)){
                titles.add(title);
            }
        }
        c.close();
        return c;
    }
}

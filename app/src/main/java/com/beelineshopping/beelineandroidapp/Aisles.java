package com.beelineshopping.beelineandroidapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.database.DatabaseUtils;
import android.widget.Toast;

import com.beelineshopping.beelineandroidapp.tasks.Ingredient;

import java.util.ArrayList;

public class Aisles extends AppCompatActivity {

    // Holds the name of the current list
    private String listName;

    // Hold all the ingredient objects
    private ArrayList<Ingredient> ingredients;

    // Variables to bind to the three listViews
    private ListView sectionOneListView;
    private ListView sectionTwoListView;
    private ListView sectionThreeListView;

    // Hold the lists that are binded to the listView
    private ArrayAdapter<String> sectionOneListAdapter;
    private ArrayAdapter<String> sectionTwoListAdapter;
    private ArrayAdapter<String> sectionThreeListAdapter;

    // Need to create String objects to hold the ingredient names
    private ArrayList<String> sectionOneItems;
    private ArrayList<String> sectionTwoItems;
    private ArrayList<String> sectionThreeItems;

    // TextView for the top of the screen
    private TextView title;

    // Button for the button
    private Button nextButton;

    private String[] aisleOrder;
    private int currentAisleIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_aisles);

        ingredients = new ArrayList<Ingredient>();

        // Hold the name of the current shopping list. Passed from the parent Activity
        listName = getIntent().getStringExtra("listName");

        fillList();

        //aisleOrder = new String[] {"Aisle 1", "Aisle 2", "Aisle 3"};
        aisleOrder = new String[] {"left", "back", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16","17", "right"};

        sectionOneListView = (ListView)findViewById(R.id.sectionOne);
        sectionTwoListView = (ListView)findViewById(R.id.sectionTwo);
        sectionThreeListView = (ListView)findViewById(R.id.sectionThree);

        currentAisleIndex = 0;

        // Initial setting up of aisle items
        int itemsNotFound = updateAisleItems();
        // If we are at the second to last aisle then only run one more time to get us to the last aisle
        while (itemsNotFound == 1 && currentAisleIndex < aisleOrder.length - 1)
        {
            currentAisleIndex++;
            itemsNotFound = updateAisleItems();
        }

        View.OnClickListener nextAisleListener = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                currentAisleIndex++;

                // If there are no more aisles go back to the parent activity
                if (currentAisleIndex >= aisleOrder.length)
                {
                    finish();
                }
                else
                {
                    int itemsNotFound = updateAisleItems();
                    // If we are at the second to last aisle then only run one more time to get us to the last aisle
                    while (itemsNotFound == 1 && currentAisleIndex < aisleOrder.length - 1)
                    {
                        currentAisleIndex++;
                        itemsNotFound = updateAisleItems();

                        // If we have reached the last aisle
                        if (currentAisleIndex >= aisleOrder.length - 1)
                        {
                            nextButton = (Button) findViewById(R.id.nextButton);
                            nextButton.setText("Done");
                        }
                    }
                }
            }
        };

        final Button nextButton = (Button) findViewById(R.id.nextButton);

        nextButton.setOnClickListener(nextAisleListener);
    }

    public void fillList()
    {
        BeelineDbHelper mDbHelper = new BeelineDbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT name, section, aisle FROM ShoppingList WHERE list_title = '" + listName + "'";

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext())
        {
            ingredients.add(new Ingredient(c.getString(0), c.getString(2), c.getString(1)));
        }
        c.close();
    }

    public int updateAisleItems()
    {
        sectionOneItems = new ArrayList<String>();
        sectionTwoItems = new ArrayList<String>();
        sectionThreeItems = new ArrayList<String>();

        for (int i = 0; i < ingredients.size(); i++)
        {   // Check the aisle
            // Toast.makeText(this, ingredients.get(i).getAisle() + " " + aisleOrder[currentAisleIndex], Toast.LENGTH_LONG).show();
            if (ingredients.get(i).getAisle().equals(aisleOrder[currentAisleIndex])) {
                // Toast.makeText(this, "Matching Aisle!", Toast.LENGTH_LONG).show();
                // Toast.makeText(this, ingredients.get(i).getSection(), Toast.LENGTH_LONG).show();
                //Check the section
                if (ingredients.get(i).getSection().equals("1")) {
                    sectionOneItems.add(ingredients.get(i).getName());
                }
                else if (ingredients.get(i).getSection().equals("2")) {
                    sectionTwoItems.add(ingredients.get(i).getName());
                }
                else if (ingredients.get(i).getSection().equals("3")) {
                    sectionThreeItems.add(ingredients.get(i).getName());
                }
            }
        }

        // Update the ListAdapters
        sectionOneListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sectionOneItems);
        sectionTwoListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sectionTwoItems);
        sectionThreeListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sectionThreeItems);

        // Update the ListViews
        sectionOneListView.setAdapter(sectionOneListAdapter);
        sectionTwoListView.setAdapter(sectionTwoListAdapter);
        sectionThreeListView.setAdapter(sectionThreeListAdapter);

        // Make ListViews grey if a section has no items
        if (sectionOneItems.size() == 0)
            sectionOneListView.setBackground(ContextCompat.getDrawable(this, R.drawable.greyborder));
        else
            sectionOneListView.setBackground(ContextCompat.getDrawable(this, R.drawable.whiteborder));

        if (sectionTwoItems.size() == 0)
            sectionTwoListView.setBackground(ContextCompat.getDrawable(this, R.drawable.greyborder));
        else
            sectionTwoListView.setBackground(ContextCompat.getDrawable(this, R.drawable.whiteborder));

        if (sectionThreeItems.size() == 0)
            sectionThreeListView.setBackground(ContextCompat.getDrawable(this, R.drawable.greyborder));
        else
            sectionThreeListView.setBackground(ContextCompat.getDrawable(this, R.drawable.whiteborder));

        // Update the text at the top of the screen
        title = (TextView) findViewById(R.id.title);

        // Set the text to the next Aisle
        if (aisleOrder[currentAisleIndex].equals("left") || aisleOrder[currentAisleIndex].equals("right") || aisleOrder[currentAisleIndex].equals("back"))
        {
            title.setText(aisleOrder[currentAisleIndex].substring(0, 1).toUpperCase() + aisleOrder[currentAisleIndex].substring(1) + " Aisle");
        }
        else
            title.setText("Aisle " + aisleOrder[currentAisleIndex]);

        if (sectionOneItems.size() == 0 && sectionTwoItems.size() == 0  && sectionThreeItems.size() == 0)
        {
            return 1;
        }
        else
            return 0;
    }

}

package com.beelineshopping.beelineandroidapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.beelineshopping.beelineandroidapp.cursor_adapters.AisleAdapter;

import java.util.ArrayList;

public class AisleSwipe extends AppCompatActivity {
    Context mContext;
    private static ArrayList<String> aisle_list = new ArrayList<String>();
    private static BeelineDbHelper mDbHelper;
    private static SQLiteDatabase db;
    private static String crStr;
    private static Cursor c;
    private static DatabaseUtils dbUtils =  new DatabaseUtils();
    private static String list_name;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        list_name = getIntent().getStringExtra("listName");
        setTitle(list_name);
        setContentView(R.layout.activity_aisle_swipe);

        mContext = this;
        //AisleAdapter list1Adapter = new AisleAdapter(this,list1Cursor,0);
        mDbHelper = new BeelineDbHelper(this);
        db = mDbHelper.getReadableDatabase();
        String whereClause = "ShoppingList.list_title = '" + list_name + "'";
        c = db.query(BeelineContract.ShoppingListDetails.TABLE_NAME, null,
                whereClause, null, null, null, null);

        String crStr = dbUtils.dumpCursorToString(c);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listIn = new Intent(getApplicationContext(),ListCollectionActivity.class);
                startActivity(listIn);
//                Snackbar.make(view, "Go to lists", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_aisle_swipe, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_aisle_swipe, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            TextView curr_list_title = (TextView) rootView.findViewById(R.id.section_label);
            int aisle_index = getArguments().getInt(ARG_SECTION_NUMBER);
            String current_Aisle = aisle_list.get(aisle_index);
            //textView.setText(getString( getArguments().getInt(ARG_SECTION_NUMBER)));
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            SpannableString content = new SpannableString(getString(R.string.section_format, current_Aisle));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            textView.setText(content);


//            textView.setText(getString(R.string.section_format, current_Aisle));
            //textView.setText(getArguments().getInt(ARG_SECTION_NUMBER));

            String whereClause1 = "ShoppingList.list_title = '" + list_name + "' AND ShoppingList.aisle = '" + current_Aisle + "' AND ShoppingList.section = \'1\'";
            String whereClause2 = "ShoppingList.list_title = '" + list_name + "' AND ShoppingList.aisle = '" + current_Aisle + "' AND ShoppingList.section = \'2\'";
            String whereClause3 = "ShoppingList.list_title = '" + list_name + "' AND ShoppingList.aisle = '" + current_Aisle + "' AND ShoppingList.section = \'3\'";
            Cursor cList1 = db.query(BeelineContract.ShoppingListDetails.TABLE_NAME, null,
                    whereClause1, null, null, null, null);

            Cursor cList2 = db.query(BeelineContract.ShoppingListDetails.TABLE_NAME, null,
                    whereClause2, null, null, null, null);

            Cursor cList3 = db.query(BeelineContract.ShoppingListDetails.TABLE_NAME, null,
                    whereClause3, null, null, null, null);

            final ListView list_view1 = (ListView)rootView.findViewById(R.id.list1);
            final ListView list_view2 = (ListView)rootView.findViewById(R.id.list2);
            final ListView list_view3 = (ListView)rootView.findViewById(R.id.list3);

            AisleAdapter aaList1 = new AisleAdapter(container.getContext(),cList1,0);
            AisleAdapter aaList2 = new AisleAdapter(container.getContext(),cList2,0);
            AisleAdapter aaList3 = new AisleAdapter(container.getContext(),cList3,0);
            list_view1.setAdapter(aaList1);
            list_view2.setAdapter(aaList2);
            list_view3.setAdapter(aaList3);

            String crStr = dbUtils.dumpCursorToString(cList1);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        int aisle_count;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            //clear old data
            if(aisle_list.size() != 0){
                aisle_list.clear();
            }
            //add aisle names to
            aislesToArray();
            aisle_count = aisle_list.size();
        }

        @Override
        public Fragment getItem(int position) {
            String atest = "nada";
            if(position == 2 || position == 3){
                atest = "this is 2 or 3";
            }else{
                atest = "this is NOT 2 or 3";
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            //return number of unique aisles
            return aisle_count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    //add aisle names to array with no repeats
    private void aislesToArray(){
        String aisleName;
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++){
            aisleName = c.getString(c.getColumnIndex("aisle"));
            if(!aisle_list.contains(aisleName)){
                aisle_list.add(aisleName);
            }
            c.moveToNext();
        }
    }
}

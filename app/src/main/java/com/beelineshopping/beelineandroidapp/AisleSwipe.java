package com.beelineshopping.beelineandroidapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.beelineshopping.beelineandroidapp.cursor_adapters.AisleAdapter;
import com.beelineshopping.beelineandroidapp.cursor_adapters.ListCollectAdapter;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import java.util.ArrayList;

public class AisleSwipe extends AppCompatActivity {
    Context mContext;

    private static ArrayList<String> aisle_list = new ArrayList<String>();
    private static BeelineDbHelper mDbHelper;
    private static SQLiteDatabase db;
    private static Cursor c;
    private static DatabaseUtils dbUtils =  new DatabaseUtils();
    private static String list_name;

    public NiftyDialogBuilder dialogBuilder;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    ListCollectAdapter list_dialog_adapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //basic setup
        mContext = this;
        mDbHelper = new BeelineDbHelper(this);
        db = mDbHelper.getReadableDatabase();
        //Get list to be displayed
        list_name = getIntent().getStringExtra("listName");

        //Display list becomes first in cursor if user is coming from login view
        if(list_name == null){
            c = db.query(BeelineContract.ShoppingListDetails.TABLE_NAME, new String[]{"list_title"},
                    null, null, null, null, null);
            String crStr = dbUtils.dumpCursorToString(c);
            if(c.getCount() != 0) {
                c.moveToFirst();

                list_name = c.getString(c.getColumnIndex("list_title"));
            }else{
                list_name = "No lists have been created";
            }
        }
        setTitle(list_name);
        setContentView(R.layout.activity_aisle_swipe);

        //Dialog box that will hold website link dialog
        dialogBuilder= NiftyDialogBuilder.getInstance(this);

        //retrieve data
        mDbHelper = new BeelineDbHelper(this);
        db = mDbHelper.getReadableDatabase();
        String whereClause = "ShoppingList.list_title = '" + list_name + "'";
        c = db.query(BeelineContract.ShoppingListDetails.TABLE_NAME, null,
                whereClause, null, null, null, null);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Custom dialog layout=====================================================================
        ArrayList<String> mLists = getTitles();
        View view = getLayoutInflater().inflate(R.layout.dialogplus, null);
        list_dialog_adapter = new ListCollectAdapter(this,mLists);
        //shopAisles("Example List");
        //=========================================================================================

        //List button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogList(view);
            }
        });

        //Refresh button
        FloatingActionButton refresh = (FloatingActionButton) findViewById(R.id.fab_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_reload = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main_reload);
            }
        });

        //Website button
        FloatingActionButton help = (FloatingActionButton) findViewById(R.id.fab_help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder
                        .withTitle("Beeline Shopping")
                        .withIcon(getResources().getDrawable(R.drawable.web_white))
                        .withDialogColor("#3F51B5")
                        .withButton1Text("Go To Website")
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                                myWebLink.setData(Uri.parse("http://45.55.5.83/shopping_lists"));
                                startActivity(myWebLink);
                            }
                        })
                        .show();
            }
        });

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

    public void dialogList(View view){
        ListView modeList = new ListView(this);
        //modeList.setAdapter(list_dialog);
        //dialog_list.setAdapter(list_dialog_adapter);
        DialogPlus dialog = DialogPlus.newDialog(mContext)
                //.setContentHolder(new ViewHolder(R.layout.dialogplus))
                .setContentHolder(new ListHolder())
                        //.setContentHeight(900)
                .setGravity(Gravity.CENTER)
                .setHeader(R.layout.dialogplus)
                .setContentBackgroundResource(R.drawable.bckgrnd_dialog)
                .setInAnimation(R.anim.abc_fade_in)
                .setAdapter(list_dialog_adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        if (item != null) {
                            shopAisles(item.toString());
                        }

                    }
                })
                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();
    }

    public void shopAisles(String listName) {
        Intent intent = new Intent(AisleSwipe.this, AisleSwipe.class);
        intent.putExtra("listName", listName);

        startActivity(intent);
    }



    //A placeholder fragment containing a simple view.
    public static class PlaceholderFragment extends Fragment {
        //The fragment argument representing the section number for this fragment.
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        // Returns a new instance of this fragment for the given section number.
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

            SpannableString content = new SpannableString(getString(R.string.section_format, current_Aisle));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            textView.setText(content);


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


      //A {@link FragmentPagerAdapter} that returns a fragment corresponding to
      //one of the sections/tabs/pages.
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
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

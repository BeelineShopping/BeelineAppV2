package com.beelineshopping.beelineandroidapp;

import android.provider.BaseColumns;

/**
 * Created by Shelby on 1/27/2016.
 */
public class FoodContract {
        public FoodContract(){};

        //Inner class that defines the table contents
        public static abstract class FoodDetails implements BaseColumns {
            public static final String TABLE_NAME = "Details";
            public static final String COLUMN_NAME_ENTRY_ID = "entryid";
            public static final String COLUMN_NAME_TITLE = "title";
            public static final String COLUMN_NAME_DESCRIPTION = "description";
            public static final String COLUMN_NAME_NULLABLE = null;
        }
}

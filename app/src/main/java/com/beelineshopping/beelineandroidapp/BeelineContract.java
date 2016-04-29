package com.beelineshopping.beelineandroidapp;

import android.provider.BaseColumns;

/**
 * Created by Shelby on 2/12/2016.
 */
public class BeelineContract {
    public BeelineContract(){};

    //Inner class that defines the table contents
    public static abstract class StoreDetails implements BaseColumns {
        public static final String TABLE_NAME = "StoreDetails";
        public static final String COLUMN_NAME_ENTRY_ID = "432";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_NULLABLE = null;
    }

    public static abstract class FoodDetails implements BaseColumns {
        public static final String TABLE_NAME = "FoodDetails";
        public static final String COLUMN_NAME_ENTRY_ID = "food_id";
        public static final String COLUMN_NAME_AISLE = "aisle_number";
        public static final String COLUMN_NAME_SECTION = "section";
        public static final String COLUMN_NAME_NULLABLE = null;
    }

    public static abstract class ShoppingListDetails implements BaseColumns {
        public static final String TABLE_NAME = "ShoppingList";
        public static final String COLUMN_NAME_ENTRY_ID  = "entryid";
        public static final String COLUMN_NAME_LIST_TITLE = "list_title";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SECTION = "section";
        public static final String COLUMN_NAME_AISLE = "aisle";
        public static final String COLUMN_NAME_STORE = "store";
        public static final String COLUMN_NAME_NULLABLE = null;
    }

    public static abstract class UserDetails implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_USER_ID  = "user_id";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        public static final String COLUMN_NAME_UPDATED_AT = "updated_at";
        public static final String COLUMN_NAME_NULLABLE = null;
    }
}

package com.beelineshopping.beelineandroidapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shelby on 2/12/2016.
 */
public class BeelineDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_SHOPPING_LIST_DETAILS_TABLE =
            "CREATE TABLE " + BeelineContract.ShoppingListDetails.TABLE_NAME + " (" +
                    BeelineContract.ShoppingListDetails._ID + " INTEGER PRIMARY KEY," +
                    BeelineContract.ShoppingListDetails.COLUMN_NAME_LIST_TITLE  + TEXT_TYPE + COMMA_SEP +
                    BeelineContract.ShoppingListDetails.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    BeelineContract.ShoppingListDetails.COLUMN_NAME_SECTION + TEXT_TYPE + COMMA_SEP +
                    BeelineContract.ShoppingListDetails.COLUMN_NAME_AISLE + TEXT_TYPE + COMMA_SEP +
                    BeelineContract.ShoppingListDetails.COLUMN_NAME_STRIKEOUT + TEXT_TYPE + " DEFAULT \'No\'" + COMMA_SEP +
                    BeelineContract.ShoppingListDetails.COLUMN_NAME_STORE + TEXT_TYPE +
                    " )";
    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + BeelineContract.UserDetails.TABLE_NAME + " (" +
                    BeelineContract.UserDetails._ID + " INTEGER PRIMARY KEY," +
                    BeelineContract.UserDetails.COLUMN_NAME_USER_ID  + TEXT_TYPE + COMMA_SEP +
                    BeelineContract.UserDetails.COLUMN_NAME_EMAIL  + TEXT_TYPE + COMMA_SEP +
                    BeelineContract.UserDetails.COLUMN_NAME_USERNAME  + TEXT_TYPE + COMMA_SEP +
                    BeelineContract.UserDetails.COLUMN_NAME_FIRST_NAME  + TEXT_TYPE + COMMA_SEP +
                    BeelineContract.UserDetails.COLUMN_NAME_LAST_NAME  + TEXT_TYPE + COMMA_SEP +
                    BeelineContract.UserDetails.COLUMN_NAME_CREATED_AT  + TEXT_TYPE + COMMA_SEP +
                    BeelineContract.UserDetails.COLUMN_NAME_UPDATED_AT  + TEXT_TYPE +
                    " )";



    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BeelineContract.ShoppingListDetails.TABLE_NAME;
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 34;
    public static final String DATABASE_NAME = "Food.db";

    public BeelineDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String test = SQL_CREATE_SHOPPING_LIST_DETAILS_TABLE;
        System.out.println("begin to execute tables");
        try {
            db.execSQL(SQL_CREATE_SHOPPING_LIST_DETAILS_TABLE);
            db.execSQL(SQL_CREATE_USER_TABLE);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}

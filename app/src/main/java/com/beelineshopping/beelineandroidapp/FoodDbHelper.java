package com.beelineshopping.beelineandroidapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.beelineshopping.beelineandroidapp.FoodContract;

/**
 * Created by Shelby on 1/27/2016.
 */
public class FoodDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FoodContract.FoodDetails.TABLE_NAME + " (" +
                    FoodContract.FoodDetails._ID + " INTEGER PRIMARY KEY," +
                    FoodContract.FoodDetails.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    FoodContract.FoodDetails.COLUMN_NAME_TITLE + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FoodContract.FoodDetails.TABLE_NAME;
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Food.db";

    public FoodDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}

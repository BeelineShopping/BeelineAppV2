package com.beelineshopping.beelineandroidapp.cursor_adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beelineshopping.beelineandroidapp.BeelineContract;
import com.beelineshopping.beelineandroidapp.BeelineDbHelper;
import com.beelineshopping.beelineandroidapp.R;

import java.sql.SQLDataException;
import java.sql.SQLException;

/**
 * Created by Shelby on 2/5/2016.
 */
public class AisleAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;
    private DatabaseUtils dbUtils;
    Cursor mCursor;
    SQLiteDatabase db;
    ContentValues values;

    Context mContext;
    public AisleAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mCursor = c;

        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;

        //String crStr = dbUtils.dumpCursorToString(c);
        //String astring = "astring";
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.list_adapter_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String crStr0 = dbUtils.dumpCursorToString(cursor);
        final TextView textViewTitle = (TextView) view.findViewById(R.id.item);
        String title = cursor.getString(cursor.getColumnIndex("name"));
        textViewTitle.setText(title);


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                //Strike out-------------------------------------
                setState(textViewTitle);
            }
        });
    }

    public Cursor getCursor(){
        return mCursor;
    }

    public void setState(TextView textViewTitle){
        Cursor cCursor = getCursor();

        BeelineDbHelper mDbHelper = new BeelineDbHelper(mContext);

        db = mDbHelper.getWritableDatabase();
        values = new ContentValues();
        String id_so = cCursor.getString(cCursor.getColumnIndex("_id"));
        String query = "SELECT _id,strikeOut FROM ShoppingList WHERE _id = " + id_so;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String crStr = dbUtils.dumpCursorToString(c);
        String strikeOut = c.getString( c.getColumnIndex( "strikeOut") );


        String crStr2 = dbUtils.dumpCursorToString(cCursor);

        if(strikeOut.equals("No")){
            values.put(BeelineContract.ShoppingListDetails.COLUMN_NAME_STRIKEOUT, "Yes");
            db.update(BeelineContract.ShoppingListDetails.TABLE_NAME, values, "_id = " + id_so, null);
            textViewTitle.setTextColor(Color.GRAY);
            textViewTitle.setPaintFlags(textViewTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            values.put(BeelineContract.ShoppingListDetails.COLUMN_NAME_STRIKEOUT, "No");
            db.update(BeelineContract.ShoppingListDetails.TABLE_NAME, values, "_id = " + id_so, null);
            textViewTitle.setTextColor(Color.parseColor("#00806d"));
            textViewTitle.setPaintFlags(0);
        }
    }
}

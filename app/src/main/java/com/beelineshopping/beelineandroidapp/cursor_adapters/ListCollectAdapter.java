package com.beelineshopping.beelineandroidapp.cursor_adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.beelineshopping.beelineandroidapp.R;

/**
 * Created by Shelby on 5/20/2016.
 */
public class ListCollectAdapter extends CursorAdapter{
    private LayoutInflater cursorInflater;
    private DatabaseUtils dbUtils;

    Context mContext;
    public ListCollectAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;

        String crStr = dbUtils.dumpCursorToString(c);
        String astring = "astring";
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.list_adapter_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView textViewTitle = (TextView) view.findViewById(R.id.item);
        String title = cursor.getString( cursor.getColumnIndex( "list_title") );
        textViewTitle.setText(title);
    }
}

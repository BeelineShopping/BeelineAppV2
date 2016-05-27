package com.beelineshopping.beelineandroidapp.cursor_adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.beelineshopping.beelineandroidapp.R;

import java.util.ArrayList;

/**
 * Created by Shelby on 5/20/2016.
 */
public class ListCollectAdapter extends ArrayAdapter<String> {
    Context mcontext;
    ArrayList<String> mTitles;
    public ListCollectAdapter(Context context, ArrayList<String> titles) {
        super(context, 0, titles);
        mcontext = context;
        mTitles = titles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_collection_adapter_layout, parent, false);
        }
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.item);
        String title = mTitles.get(position);
        textViewTitle.setText(title);
        // Return the completed view to render on screen
        return convertView;
    }
}

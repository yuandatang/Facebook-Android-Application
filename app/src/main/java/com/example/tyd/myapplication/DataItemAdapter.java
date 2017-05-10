package com.example.tyd.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyd.myapplication.model.DataItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DataItemAdapter extends ArrayAdapter<DataItem> {

    Context context;
    List<DataItem> mDataItems;
    //LayoutInflater mInflater;
    ArrayList<DataItem> data = null;
    int layoutResourceId;

    public DataItemAdapter(Context context, int layoutResourceId, ArrayList<DataItem> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        View row = convertView;
        UsersHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UsersHolder();
            holder.personIcon = (ImageView)row.findViewById(R.id.legi_img);
            holder.summary = (TextView)row.findViewById(R.id.legi_name);

            row.setTag(holder);
        }
        else
        {
            holder = (UsersHolder) row.getTag();
        }

        DataItem person = data.get(position);

        //holder.name.setText(person.getLast_name() + ", " + person.getFirst_name());

        String url = person.img;
        Picasso.with(context).load(url).into(holder.personIcon);

        String summary;
        summary = person.name;
        holder.summary.setText(summary);
        return row;
    }

    static class UsersHolder
    {
        ImageView personIcon;
        TextView summary;
    }
}

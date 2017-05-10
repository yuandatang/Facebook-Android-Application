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
import com.example.tyd.myapplication.model.PostItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PostItemAdapter extends ArrayAdapter<PostItem> {

    Context context;
    ArrayList<PostItem> data = null;
    int layoutResourceId;

    public PostItemAdapter(Context context, int layoutResourceId, ArrayList<PostItem> objects) {
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


            holder.personIcon = (ImageView)row.findViewById(R.id.post_img);
            holder.name = (TextView) row.findViewById(R.id.post_name);
            holder.time = (TextView) row.findViewById(R.id.post_time);
            holder.summary = (TextView)row.findViewById(R.id.post_message);



            row.setTag(holder);
        }
        else
        {
            holder = (UsersHolder) row.getTag();
        }

        PostItem person = data.get(position);

        //holder.name.setText(person.getLast_name() + ", " + person.getFirst_name());

        String url = person.img;
        Picasso.with(context).load(url).into(holder.personIcon);

        String summary;
        summary = person.summary;
        holder.summary.setText(summary);

        String name;
        name = person.name;
        holder.name.setText(name);

        String time;
        time = person.time;
        holder.time.setText(time);

        return row;
    }

    static class UsersHolder
    {
        ImageView personIcon;
        TextView name;
        TextView time;
        TextView summary;
    }
}

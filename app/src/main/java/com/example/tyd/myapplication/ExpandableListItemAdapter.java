package com.example.tyd.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyd.myapplication.model.ImageItem;
import com.squareup.picasso.Picasso;

public class ExpandableListItemAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<ImageItem>> _listDataChild;


    public ExpandableListItemAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, ArrayList<ImageItem>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        //final String childText = (String) getChild(groupPosition, childPosition);
        UsersHolder holder = null;
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.album_list_item, null);

            holder = new UsersHolder();
            holder.image1 = (ImageView) convertView.findViewById(R.id.lblListItem);
            holder.image2 = (ImageView) convertView.findViewById(R.id.lblListItem2);

            convertView.setTag(holder);

        }
        else{
            holder = (UsersHolder) convertView.getTag();
        }

        ImageItem person = _listDataChild.get(headerTitle).get(childPosition);


        String test1="https://scontent.xx.fbcdn.net/v/t1.0-1/12294812_182587018755153_5700358192209376194_n.jpg?oh=7d0882fb7a5d2c2e9fdd50940dc945cb&oe=59753117";
        String test2="https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/16508358_1456199354404512_2718016290597062250_n.jpg?oh=cae6cc5b67e98bf3253dd55c830e016d&oe=598F524F";

        String s1 = "https://graph.facebook.com/v2.8/";
        String s2 = "/picture?&access_token=EAACRpkHZCDQABAIAPCSlZA7N16qhKTMkjAKD96BCKNNiBOVjxMKYsJ0Jl6fxI3cMwBSontcHmBfvH471ZCsGzgXsT4jNUsJjhcUdVboEz8vKEK6zpSLZAMTbzK4sLeaOrVqZAOu3FxBeQNB61iwraQcnRZCcRLFVYZD";

        String url = s1+person.img+s2;
        String url2 = s1+person.img2+s2;


        Picasso.with(_context).load(url).into(holder.image1);
        Picasso.with(_context).load(url2).into(holder.image2);
        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.album_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class UsersHolder
    {
        ImageView image1;
        ImageView image2;
    }
}
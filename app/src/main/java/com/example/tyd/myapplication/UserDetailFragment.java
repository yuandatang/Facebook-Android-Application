package com.example.tyd.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by tyd on 2017/4/22.
 */

public class UserDetailFragment extends android.support.v4.app.Fragment {

    private FragmentTabHost mTabHost;

    public UserDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String id = getArguments().getString("id");
        String name =getArguments().getString("name");
        String img = getArguments().getString("img");

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("name", name);
        bundle.putString("img", img);

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.legi_up_frame2);
        mTabHost.addTab(mTabHost.newTabSpec("album").setIndicator("",getResources().getDrawable(R.drawable.album2)), Detail_album.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec("post").setIndicator("",getResources().getDrawable(R.drawable.post2)),Detail_post.class, bundle);
        mTabHost.setCurrentTab(0);
        //return mTabHost;
        return mTabHost;
    }

}

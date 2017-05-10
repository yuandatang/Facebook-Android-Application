package com.example.tyd.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tyd.myapplication.model.DataItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class FHomeFragment extends Fragment {
    private FragmentTabHost mTabHost;
    public static HashSet<DataItem> user_fav = new HashSet<>();
    public static HashSet<DataItem> page_fav = new HashSet<>();
    public static HashSet<DataItem> place_fav = new HashSet<>();
    public static HashSet<DataItem> event_fav = new HashSet<>();
    public static HashSet<DataItem> group_fav = new HashSet<>();
    public static ArrayList<DataItem> record =new ArrayList<>();
    public static int NUM_ITEMS_PAGE   = 10;
    public static int current_page =0;
    public static int increment =0;
    public static int PAGE_LENGTH =0;
    public static int pageCount =0;
    public static String type = "user";


    public String keyword = null;

    public FHomeFragment() {
        // Required empty public constructor
    }

    public void setKeyword(String s){
        keyword = s;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String strtext = getArguments().getString("keyword");

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.legi_up_frame);

        Bundle bundle = new Bundle();
        bundle.putString("keyword", strtext);


        mTabHost.addTab(mTabHost.newTabSpec("state").setIndicator("",getResources().getDrawable(R.drawable.user2)), FHome_user.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec("house").setIndicator("",getResources().getDrawable(R.drawable.page2)),FHome_page.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec("senate").setIndicator("",getResources().getDrawable(R.drawable.event2)),FHome_event.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec("place").setIndicator("",getResources().getDrawable(R.drawable.place2)),FHome_place.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec("group").setIndicator("",getResources().getDrawable(R.drawable.group2)),FHome_group.class, bundle);
        mTabHost.setCurrentTab(0);
        return mTabHost;
    }

}

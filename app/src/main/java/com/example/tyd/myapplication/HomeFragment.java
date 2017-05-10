package com.example.tyd.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static FragmentTabHost mTabHost;

    public String keyword = null;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void setKeyword(String s){
        keyword = s;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Results");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String strtext = getArguments().getString("keyword");

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.legi_up_frame);

        Bundle bundle = new Bundle();
        bundle.putString("keyword", strtext);
        mTabHost.addTab(mTabHost.newTabSpec("state").setIndicator("",getResources().getDrawable(R.drawable.user2)), Home_user.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec("house").setIndicator("",getResources().getDrawable(R.drawable.page2)),Home_page.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec("senate").setIndicator("",getResources().getDrawable(R.drawable.event2)),Home_event.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec("place").setIndicator("",getResources().getDrawable(R.drawable.place2)),Home_place.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec("group").setIndicator("",getResources().getDrawable(R.drawable.group2)),Home_group.class, bundle);
        mTabHost.setCurrentTab(0);
        return mTabHost;
    }
}

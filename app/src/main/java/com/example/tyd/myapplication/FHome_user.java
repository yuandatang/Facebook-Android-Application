package com.example.tyd.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.tyd.myapplication.model.DataItem;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FHome_user extends Fragment {
    private ListView lv;
    ArrayList<DataItem> list=new ArrayList<>();
    View rootView=null;

    public FHome_user() {
        // Required empty public constructor
    }

    @Override
    public void onResume(){
        super.onResume();
        getData();
        handleList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FHomeFragment.type ="user";
        rootView = inflater.inflate(R.layout.fragment_fhome_user, container, false);
        lv = (ListView)rootView.findViewById(R.id.aaa_list_item);
        list=new ArrayList<>(FHomeFragment.user_fav);
        // Inflate the layout for this fragment
        handleList();
        return rootView;
    }
    public void getData(){
        list = new ArrayList<>(FHomeFragment.user_fav);
    }
    public void handleList(){
        DataItemAdapter adapter = new DataItemAdapter(getContext(), R.layout.user_list_item,list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataItem person = (DataItem) lv.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(),FDetailsActivity.class);
                //display star in proper way
                if(FHomeFragment.user_fav.contains(person)){
                    ImageView selectedImage = (ImageView) view.findViewById(R.id.legi_img2);
                    selectedImage.setImageResource(R.drawable.star);
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", person.id);
                bundle.putString("name",person.name);
                bundle.putString("img",person.img);
                bundle.putString("type","user");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}

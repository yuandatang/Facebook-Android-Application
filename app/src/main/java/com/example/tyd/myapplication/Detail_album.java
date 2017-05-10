package com.example.tyd.myapplication;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

import com.example.tyd.myapplication.model.DataItem;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.tyd.myapplication.model.HttpHelper;
import com.example.tyd.myapplication.model.ImageItem;
import com.example.tyd.myapplication.model.PostItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class Detail_album extends Fragment implements OnClickListener{
    private ExpandableListView lv;
    private TextView haha;
    ExpandableListItemAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, ArrayList<ImageItem>> listDataChild;
    ArrayList<PostItem> dataItemsList;
    String img;
    String name;
    View rootView;
    View rootView2;

    public Detail_album() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            String id = getArguments().getString("id");
            img = getArguments().getString("img");
            name = getArguments().getString("name");
            String s= "http://lowcost-env.tmfidancxy.us-west-1.elasticbeanstalk.com/index.php?flag=false&id="+id;
            URL url = new URL(s);
            new LegiStateTask().execute(url);

        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        //int length=hello.length();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("State", "Come State");
        rootView = inflater.inflate(R.layout.album_list_super, container, false);
        lv = (ExpandableListView)rootView.findViewById(R.id.lvExp);
        if(listDataHeader!=null) {
            rootView = inflater.inflate(R.layout.album_list_super, container, false);
            lv = (ExpandableListView)rootView.findViewById(R.id.lvExp);
            final ExpandableListItemAdapter adapter = new ExpandableListItemAdapter(getContext(), listDataHeader, listDataChild);
            lv.setAdapter(adapter);

            lv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    for (int i = 0, count = adapter.getGroupCount(); i < count; i++) {
                        if (groupPosition != i) {
                            lv.collapseGroup(i);
                        }
                    }
                }
            });
            //handleList();
        }
//        else if(listDataHeader==null){
//            rootView = inflater.inflate(R.layout.fragment_album_or_post_empty, container, false);
//            haha = (TextView) rootView.findViewById(R.id.emptyView);
//            haha.setText("in oncreateView");
//        }
        return rootView;
    }

    private void handleList(){
        if(listDataHeader!=null) {
            ExpandableListItemAdapter adapter = new ExpandableListItemAdapter(getContext(), listDataHeader, listDataChild);
            lv.setAdapter(adapter);
            lv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    for (int i = 0, count = lv.getExpandableListAdapter().getGroupCount(); i < count; i++) {
                        if (groupPosition != i) {
                            lv.collapseGroup(i);
                        }
                    }
                }
            });
        }
        else{
            haha.setText("in handleList ");
        }
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        Log.d("State", "Come State");
//        rootView = inflater.inflate(R.layout.album_list_super, container, false);
//        lv = (ExpandableListView)rootView.findViewById(R.id.lvExp);
//        rootView2 = inflater.inflate(R.layout.fragment_album_or_post_empty, container, false);
//        haha = (TextView) rootView.findViewById(R.id.emptyView);
//        if(listDataHeader!=null) {
//            ExpandableListItemAdapter adapter = new ExpandableListItemAdapter(getContext(), listDataHeader, listDataChild);
//            lv.setAdapter(adapter);
//            //handleList();
//        }
//        else if(listDataHeader==null){
//            haha.setText("in onCreateView ");
//            return rootView2;
//        }
//        return rootView;
//    }



    @Override
    public void onClick(View view){
        TextView selectedIndex = (TextView) view;
        //lv.setSelection(mapIndex.get(selectedIndex.getText()));
    }

    private class LegiStateTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... url) {
            Reader reader = null;
            HttpURLConnection conn = null;
            String fake = "";

            try {
                conn = (HttpURLConnection) url[0].openConnection();
                InputStream stream = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    fake=fake+inputString;
                }

            } catch (IOException e){
                e.printStackTrace();
            }
            finally {
                if (reader != null)
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            return fake;
        }

        @Override
        protected void onPostExecute(String s){
            //Log.d("result", Integer.toString(Legislators.size()));
            String result=new String(s);
            JSONObject mainObject = null;
            JSONArray data = null;

            listDataHeader = new ArrayList<String>();

            listDataChild = new HashMap<String, ArrayList<ImageItem>>();

            try {
                mainObject = new JSONObject(s);
                JSONObject temp = mainObject.getJSONObject("albums");
                data = temp.getJSONArray("data");
                for(int i=0;i<data.length();i++){
                    JSONObject obj = data.getJSONObject(i);
                    String name = obj.getString("name");
                    JSONObject photos = obj.getJSONObject("photos");
                    JSONArray datass = photos.getJSONArray("data");
                    listDataHeader.add(name);

                    String source1 = datass.getJSONObject(0).getString("id");
                    String source2 = datass.getJSONObject(1).getString("id");
                    ImageItem item=null;
                    item = new ImageItem(source1,source2);
                    ArrayList<ImageItem> top250 = new ArrayList<ImageItem>();

                    top250.add(item);

                    listDataChild.put(listDataHeader.get(i),top250); // Header, Child data
                    //DataItem test=new DataItem("asd","sdf","sdf");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            handleList();
        }
    }
}
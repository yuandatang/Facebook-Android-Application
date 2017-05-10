package com.example.tyd.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.tyd.myapplication.model.HttpHelper;
import com.example.tyd.myapplication.model.PostItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Detail_post extends Fragment{
    private ListView lv;
    private LinearLayout side_index;
    private Map<String, Integer> mapIndex;
    private String hello;
    ArrayList<PostItem> dataItemsList;
    String img;
    String name;

    public Detail_post() {
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
        View rootView = inflater.inflate(R.layout.fragment_home_user, container, false);
        lv = (ListView)rootView.findViewById(R.id.aaa_list_item);
        if(dataItemsList != null) handleList();
        return rootView;
    }

    private void handleList(){
        PostItemAdapter adapter = new PostItemAdapter(getContext(), R.layout.post_list_item, dataItemsList);
        lv.setAdapter(adapter);
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
            hello = new String (result);
            JSONObject mainObject = null;
            JSONArray data = null;
            dataItemsList=new ArrayList<>();

            try {
                mainObject = new JSONObject(result);
                JSONObject temp = mainObject.getJSONObject("posts");
                data = temp.getJSONArray("data");
                for(int i=0;i<data.length();i++){
                    JSONObject single = data.getJSONObject(i);
                    String message = single.getString("message");
                    String created_time = single.getString("created_time");
                    PostItem item=null;

                    item = new PostItem(img,name,created_time,message);
                    //DataItem test=new DataItem("asd","sdf","sdf");
                    dataItemsList.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            handleList();
        }
    }

}
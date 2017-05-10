package com.example.tyd.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.tyd.myapplication.FHomeFragment.increment;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_event extends Fragment implements OnClickListener{
    private ListView lv;
    private LinearLayout side_index;
    private Map<String, Integer> mapIndex;
    private String keyword;
    ArrayList<DataItem> dataItemsList;
    ArrayList<DataItem> sort;
    Button btn_next;
    Button btn_prev;

    public Home_event() {
        // Required empty public constructor
        FHomeFragment.increment = 0;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FHomeFragment.type ="event";
        btn_next = (Button) getActivity().findViewById(R.id.next);
        btn_prev = (Button) getActivity().findViewById(R.id.prev);
        btn_next.setEnabled(true);
        CheckEnable();
        //button
        btn_next.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                increment++;
                int i=FHomeFragment.pageCount;
                increment = Math.min(increment,3);
                CheckEnable();
                loadList(increment);
                handleList();
            }
        });
        btn_prev.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                increment--;
                increment = Math.max(increment,0);
                CheckEnable();
                loadList(increment);
                handleList();
            }
        });
        try {
            keyword = getArguments().getString("keyword");
            String s = "http://lowcost-env.tmfidancxy.us-west-1.elasticbeanstalk.com/index.php?flag=true&type=event&keyword="+keyword;
            URL url = new URL(s);
            new LegiStateTask().execute(url);

        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        //int length=hello.length();

    }

    /**
     * Method for enabling and disabling Buttons
     */
    private void CheckEnable()
    {
        if(increment+1 == 3)
        {
            btn_next.setEnabled(false);
        }
        else if(increment == 0)
        {
            btn_prev.setEnabled(false);
        }
        else
        {
            btn_prev.setEnabled(true);
            btn_next.setEnabled(true);
        }
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
        loadList(FHomeFragment.increment);
        DataItemAdapter adapter = new DataItemAdapter(getContext(), R.layout.user_list_item, sort);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataItem person = (DataItem) lv.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(),DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", person.id);
                bundle.putString("name",person.name);
                bundle.putString("img",person.img);
                bundle.putString("type","event");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onClick(View view){
        TextView selectedIndex = (TextView) view;
        lv.setSelection(mapIndex.get(selectedIndex.getText()));
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
            dataItemsList=new ArrayList<>();

            try {
                mainObject = new JSONObject(result);
                data = mainObject.getJSONArray("data");
                System.out.println(data.length());
                for(int i=0;i<data.length();i++){
                    JSONObject single = data.getJSONObject(i);
                    String name = single.getString("name");
                    String id = single.getString("id");
                    String image = single.getJSONObject("picture").getJSONObject("data").getString("url");
                    DataItem item=null;
                    if(!name.equals(""))
                        item = new DataItem(image,name,id);
                    //DataItem test=new DataItem("asd","sdf","sdf");
                    dataItemsList.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            FHomeFragment.record = dataItemsList;
            int val = Math.min(dataItemsList.size(),25)%10;
            val = val==0?0:1;
            FHomeFragment.pageCount = dataItemsList.size()/10;
            handleList();
        }
    }
    /**
     * Method for loading data in listview
     * @param number
     */
    private void loadList(int number)
    {
        sort = new ArrayList();

        int start = number * 10;
        for(int i=start;i<(start)+10;i++)
        {
            if(i<dataItemsList.size())
            {
                sort.add(dataItemsList.get(i));
            }
            else
            {
                break;
            }
        }
    }

}
package com.example.tyd.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;

import static com.example.tyd.myapplication.FHomeFragment.increment;

public class ResultActivity extends AppCompatActivity
        {

   Toolbar toolbar;
            private Button btn_prev;
            private Button btn_next;
            String keyword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        FHomeFragment.record = null;
        increment =0;

        //  button stuff
//        btn_prev = (Button)findViewById(R.id.prev);
//        btn_next = (Button)findViewById(R.id.next);
//        btn_prev.setEnabled(false);

//        btn_next.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                increment++;
//                int i=FHomeFragment.pageCount;
//                increment = Math.min(increment,3);
//                CheckEnable();
//                display();
//            }
//        });
//
//        btn_prev.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View v) {
//                increment--;
//                increment = Math.max(increment,0);
//                CheckEnable();
//                display();
//            }
//        });

        Intent intent = getIntent();
        keyword = intent.getStringExtra("EXTRA_MESSAGE");



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "Back", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                finish();
            }
        });

        display();
    }
    public void display(){
        try {
            Fragment fragment = (Fragment) HomeFragment.class.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

            Bundle bundle = new Bundle();
            bundle.putString("keyword", keyword);
            fragment.setArguments(bundle);
        }
        catch (IllegalAccessException e){
            e.printStackTrace();
        }
        catch (InstantiationException e){
            e.printStackTrace();
        }
    }
}

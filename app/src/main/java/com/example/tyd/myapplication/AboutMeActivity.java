package com.example.tyd.myapplication;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AboutMeActivity extends AppCompatActivity {
    TextView student_name = null;
    TextView student_id = null;
    ImageView img = null;
    Context context;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

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

        student_id = (TextView) findViewById(R.id.student_id);
        student_name = (TextView) findViewById(R.id.student_name);
        img = (ImageView) findViewById(R.id.student_img);

        student_name.setText("Yuanda Tang");
        student_id.setText("4463226804");
        //Picasso.with(context).load("https://d1b10bmlvqabco.cloudfront.net/photos/is8askltq3x300/1476158180_375.png").into(img);
    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//        toolbar.setTitle("About Me");
//    }
}

package com.example.tyd.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button clearButton;
    Button searchButton;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    public Toolbar toolbar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(getBaseContext(), ResultActivity.class);
        EditText editText = (EditText) findViewById(R.id.inputText);
        String message = editText.getText().toString();

        if (message.matches("")) {
            Toast.makeText(this, "Please enter a keyword!", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);
    }

    /** Called when the user taps the Send button */
    public void setClearButton(View view) {
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.inputText);
        editText.setText("");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.favourite) {
            Log.d("aboutme","open");
            Intent intent2 = new Intent(this, FavouriteActivity.class);
            startActivity(intent2);
            return true;
        }
        else if(id == R.id.aboutMe){
            Log.d("aboutme","open");
            Intent intent2 = new Intent(this, AboutMeActivity.class);
            startActivity(intent2);
            return true;
        }
        return true;
    }

}

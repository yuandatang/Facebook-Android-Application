package com.example.tyd.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import com.example.tyd.myapplication.model.DataItem;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;



//1.Creates the Tabbed Layout – Albums and Posts
//2.Facebook Share
//3.Add to favorites
public class FDetailsActivity extends AppCompatActivity {
    public Toolbar toolbar = null;
    String type;
    String id;
    String img;
    String name;
    DataItem data=null;
    CallbackManager callbackManager;
    ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        type = bundle.getString("type");
        id = bundle.getString("id");
        img = bundle.getString("img");
        name = bundle.getString("name");
        type = bundle.getString("type");
        data = new DataItem(img,name,id);


        //back button
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


        //Fragment fragment = null;
        try {
            Fragment fragment = (Fragment) UserDetailFragment.class.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.detail_frame, fragment).commit();
            fragment.setArguments(bundle);
        }
        catch (IllegalAccessException e){
            e.printStackTrace();
        }
        catch (InstantiationException e){
            e.printStackTrace();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            FacebookSdk.sdkInitialize(getApplicationContext());
            callbackManager = CallbackManager.Factory.create();
            shareDialog = new ShareDialog(this);
            int a=1;
            int b =2;
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                        .build();
                shareDialog.show(linkContent);
                Intent intent = getIntent();
                onActivityResult(1,1,intent);
            }


            return true;
        }
        if (id == R.id.favourite){
            if(type.equals("user")) {
                for(DataItem a:FHomeFragment.user_fav){
                    if(a.id.equals(this.id)){
                        FHomeFragment.user_fav.remove(a);
                    }
                }
            }
            if(type.equals("place")) {
                for(DataItem a:FHomeFragment.place_fav){
                    if(a.id.equals(this.id)){
                        FHomeFragment.place_fav.remove(a);
                    }
                }
            }
            if(type.equals("page")) {
                for(DataItem a:FHomeFragment.page_fav){
                    if(a.id.equals(this.id)){
                        FHomeFragment.page_fav.remove(a);
                    }
                }
            }
            if(type.equals("group")) {
                for(DataItem a:FHomeFragment.group_fav){
                    if(a.id.equals(this.id)){
                        FHomeFragment.group_fav.remove(a);
                    }
                }
            }
            if(type.equals("event")) {
                for(DataItem a:FHomeFragment.event_fav){
                    if(a.id.equals(this.id)){
                        FHomeFragment.event_fav.remove(a);
                    }
                }
            }
        }
        Toast.makeText(this, "Removed from Favorites!", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

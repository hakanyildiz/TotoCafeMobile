package com.sohos.totocafemobile;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
/*
* ******BUNDLE KULLANIMI **********
* Android programlamada activiyler arası data geçişi için bundle kullanılır.
 Bundle yardımıyla bir activitye
de mevcut olan data başka bir activitye gönderilebilir.

//////////////MAIN ACTIVITY
Intent i = new Intent(MainActivity.this, SecondActivity.class);

Bundle toMain = new Bundle();
fromMain.putString("info", "Please subscribe me");
i.putExtras(fromMain);

startActivity(i);

////////SECOND ACTIVITY
Bundle fromMain = getIntent().getExtras();

String myValue = fromMain.getString("info");
*
* */
public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);

         toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer ,(DrawerLayout)findViewById(R.id.drawer_layout) ,toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Toast.makeText(this, "Hey you just hit + " +item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }

        if(id == R.id.navigate){
            startActivity(new Intent(this,Splash.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

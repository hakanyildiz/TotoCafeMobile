package com.sohos.totocafemobile.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.sohos.totocafemobile.R;

/**
 * Created by dilkom71 on 12.01.2016.
 */
public class SubActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main_appbar);

    }
}

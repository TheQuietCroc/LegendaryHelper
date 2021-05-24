package com.thequietcroc.legendary.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.thequietcroc.legendary.R;

public class FilterMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilter);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilter);
        }
    }
}
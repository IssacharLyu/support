package com.broil.support.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.broil.support.R;
import com.broil.support.utils.StatusBarCompat;

/**
 * Created by broil on 2016/6/1
 */
public class BaseActivity extends AppCompatActivity {

    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this);
        super.setContentView(R.layout.activity_toolbar);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        if (rootLayout == null) return;
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
    }
}

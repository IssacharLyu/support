package com.broil.support;

import android.content.Intent;
import android.os.Bundle;

import com.broil.support.activities.BaseActivity;
import com.broil.support.activities.RefreshActivity;

/**
 * Created by broil on 2016/6/1
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.refresh_button).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RefreshActivity.class)));
    }
}

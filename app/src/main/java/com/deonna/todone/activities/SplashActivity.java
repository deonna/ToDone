package com.deonna.todone.activities;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.deonna.todone.activities.MainActivity;
import com.deonna.todone.models.ContextHolder;

import java.util.concurrent.TimeUnit;

public class SplashActivity extends AppCompatActivity {

    private static final int DELAY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ContextHolder.setContext(this);

        SystemClock.sleep(TimeUnit.SECONDS.toMillis(DELAY));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

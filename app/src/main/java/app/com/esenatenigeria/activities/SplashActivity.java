package app.com.esenatenigeria.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import app.com.esenatenigeria.R;
import app.com.esenatenigeria.database.Db;
import app.com.esenatenigeria.utils.Utils;

/**
 * Created by Applify on 8/11/2016.
 */
public class SplashActivity extends Activity {

    int mScreenWidth, mScreenHeight;
    private int TIME_OUT = 3000;
    Context mContext;
    Utils util;
    Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = SplashActivity.this;
        getDefaults();
        openNextActivity();
    }

    void getDefaults() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mScreenWidth = displaymetrics.widthPixels;
        mScreenHeight = displaymetrics.heightPixels;
        db = new Db(getApplicationContext());
        util = new Utils(getApplicationContext());

    }

    void openNextActivity() {
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent in = new Intent(SplashActivity.this, LandingActivity.class);
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        }, TIME_OUT);
    }

}

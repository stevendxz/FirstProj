package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.adtis.fistpproj.R;

/**
 * Created by Administrator on 2015/12/8.
 */
public class SplashActivity extends Activity {
    private static final int SHOW_TIME_MIN = 3000;// 最小显示时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }

        }, SHOW_TIME_MIN);
    }
}

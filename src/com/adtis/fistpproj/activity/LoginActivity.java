package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import com.adtis.fistpproj.R;

public class LoginActivity extends Activity {
    private ImageView btn_left;
    private ImageView btn_right;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initLoginView();
    }
    private NavActivity navgativeView;
    public void initLoginView() {
        navgativeView = (NavActivity)super.findViewById(R.id.nav_login);
        navgativeView.setTitle("Login");
        btn_left = (ImageView)navgativeView.findViewById(R.id.iv_nav_back);
        btn_right = (ImageView)navgativeView.findViewById(R.id.iv_nav_right);
        btn_left.setImageResource(R.drawable.iconfont_back);
        btn_right.setImageResource(R.drawable.iconfont_send);
        navgativeView.setClickCallback(new NavActivity.ClickCallback() {

            @Override
            public void onRightClick() {
                DisplayToast("点击了右侧按钮!");
            }

            @Override
            public void onBackClick() {

                Intent intent = new Intent();
                intent.setClass(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void DisplayToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}

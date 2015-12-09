package com.adtis.fistpproj;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
    final Context context = this;
    private DrawerLayout drawerLayout = null;
    private ListView listView = null;
    private ActionBarDrawerToggle toggle = null;
    private CharSequence sequence;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        DisplayToast("Hello");
        initView();
    }
    private NavActivity navigationView;
    private void initView() {
        navigationView = (NavActivity) super.findViewById(R.id.nav_main);
        navigationView.setTitle("主页");
        navigationView.setClickCallback(new NavActivity.ClickCallback() {

            @Override
            public void onRightClick() {
                //DisplayToast("点击了右侧按钮!");
                Intent intent = new Intent();
                intent.setClass(context, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onBackClick() {
                DisplayToast("点击了返回按钮!");
                finish();
            }
        });
    }

    public void DisplayToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}

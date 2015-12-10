package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.adapter.ContentAdapter;
import com.adtis.fistpproj.model.ContentModel;
import com.nineoldandroids.view.ViewHelper;
import com.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    final Context context = this;
    private DrawerLayout mDrawerLayout = null;
    private NavActivity navigationView;
    SlidingMenu menu;
    private RelativeLayout leftLayout;
    private List<ContentModel> list;
    private ContentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        initView();
        leftLayout=(RelativeLayout) findViewById(R.id.menu_relativelayout);
        ListView listView=(ListView) leftLayout.findViewById(R.id.left_drawer);
        adapter=new ContentAdapter(this, list);
        listView.setAdapter(adapter);

/*** 初始化侧滑菜单 Begin ***/
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.layout_menu);
/*** 初始化侧滑菜单 End ***/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
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
                boolean flag = menu.isMenuShowing();
                if (!flag) {
                    menu.showMenu();
                }
            }
        });
        list=new ArrayList<ContentModel>();
        list.add(new ContentModel(R.drawable.doctoradvice2, "新闻"));
        list.add(new ContentModel(R.drawable.infusion_selected, "订阅"));
        list.add(new ContentModel(R.drawable.mypatient_selected, "图片"));
        list.add(new ContentModel(R.drawable.mywork_selected, "视频"));
        list.add(new ContentModel(R.drawable.nursingcareplan2, "跟帖"));
        list.add(new ContentModel(R.drawable.personal_selected, "投票"));
    }


    public void DisplayToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}

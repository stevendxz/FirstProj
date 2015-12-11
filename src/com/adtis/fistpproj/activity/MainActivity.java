package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.widget.Toast;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.util.CustomToast;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends Activity {
    final Context context = this;
    private DrawerLayout mDrawerLayout = null;
    private NavActivity navigationView;
    private SlidingMenu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        initView();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        navigationView = (NavActivity) mDrawerLayout.findViewById(R.id.nav_main);
        navigationView.setTitle("主页");
        navigationView.setClickCallback(new NavActivity.ClickCallback() {

            @Override
            public void onRightClick() {
                CustomToast.DisplayToast(context,"点击了设置按钮");
            }

            @Override
            public void onBackClick() {
                boolean flag = menu.isMenuShowing();
                if (!flag) {
                    menu.showMenu();
                }
            }
        });

        /*** 初始化侧滑菜单 Begin ***/
        menu = new SlidingMenu(context);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_activity);
        /*** 初始化侧滑菜单 End ***/
    }
}

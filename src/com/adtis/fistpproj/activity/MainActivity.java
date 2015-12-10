package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.adapter.ContentAdapter;
import com.adtis.fistpproj.model.ContentModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    final Context context = this;
    private DrawerLayout drawerLayout = null;
    private RelativeLayout leftlayout = null;
    private List<ContentModel> list = null;
    private ContentAdapter adapter = null;
    private ListView listView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        drawerLayout = (DrawerLayout)findViewById(R.id.main);
        leftlayout = (RelativeLayout)drawerLayout.findViewById(R.id.main_leftlayout);
        listView = (ListView)leftlayout.findViewById(R.id.left_drawer);
        //DisplayToast("Hello");
        initView();
        adapter = new ContentAdapter(this, list);
        listView.setAdapter(adapter);
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

        list = new ArrayList<ContentModel>();
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

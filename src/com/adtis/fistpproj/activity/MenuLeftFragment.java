package com.adtis.fistpproj.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.adapter.ContentAdapter;
import com.adtis.fistpproj.model.ContentModel;

import java.util.ArrayList;
import java.util.List;

public class MenuLeftFragment extends Fragment {
    private List<ContentModel> list = null;
    private ContentAdapter adapter = null;
    private LinearLayout menu_set_linearLayout = null;
    private LinearLayout menu_login_linearLayout = null;
    private Button btn_login;
    private Button btn_setting;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_activity, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list=new ArrayList<ContentModel>();
        list.add(new ContentModel(R.drawable.iconfont_home, "个人中心"));
        list.add(new ContentModel(R.drawable.iconfont_favorite, "关注"));
        list.add(new ContentModel(R.drawable.iconfont_collection, "收藏"));
        list.add(new ContentModel(R.drawable.iconfont_video, "视频"));
        list.add(new ContentModel(R.drawable.iconfont_audio, "音频"));
        list.add(new ContentModel(R.drawable.iconfont_about, "关于"));
        ListView listView=(ListView) getActivity().findViewById(R.id.left_drawer);
        adapter=new ContentAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("MSG:","点击了"+list.get(position).getText());
               switch (position) {
                    case 0:
                    case 1:
                    case 2:
                        break;
                    case 3:
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), VideoActivity.class);
                        startActivity(intent);
                    case 4:
                    case 5:
                    default:
                        break;
                }
            }
        });

        menu_set_linearLayout = (LinearLayout)getActivity().findViewById(R.id.menu_set_linearLayout);
        menu_login_linearLayout = (LinearLayout)getActivity().findViewById(R.id.menu_login_linearLayout);
        btn_setting = (Button)menu_set_linearLayout.findViewById(R.id.menu_btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MSG:","点击了设置按钮");
            }
        });
        btn_login = (Button)menu_login_linearLayout.findViewById(R.id.menu_btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MSG:","点击了登录按钮");
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}

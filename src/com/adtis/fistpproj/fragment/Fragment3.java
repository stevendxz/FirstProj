package com.adtis.fistpproj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.adapter.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Fragment3 extends Fragment implements AdapterView.OnItemClickListener {

    private static final String OPTIONS[] = {"桌面插件", "绑定微博", "天气分享", "通知与提示", "定时播报"};
    private MyListView listView_1;
    private ArrayList<Map<String, String>> listData;
    private SimpleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment3, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView_1 = (MyListView) getActivity().findViewById(R.id.mylistview_1);

        listView_1.setAdapter(getSimpleAdapter(OPTIONS));

        listView_1.setOnItemClickListener(this);

        setListViewHeightBasedOnChildren(listView_1);

    }

    /**
     * 设置第一列数据
     */
    private SimpleAdapter getSimpleAdapter(String[] options) {
        listData = new ArrayList<Map<String, String>>();

        Map<String, String> map;

        for (int i = 0; i < options.length; i++) {
            map = new HashMap<String, String>();
            map.put("text", options[i]);
            listData.add(map);
        }

        return new SimpleAdapter(getActivity(), listData,
                R.layout.list_item, new String[]{"text"},
                new int[]{R.id.tv_list_item});

    }

    /***
     * 动态设置listview的高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // params.height += 5;// if without this statement,the listview will be
        // a
        // little short
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == listView_1) {
            Map<String, String> map = listData.get(position);
            Toast.makeText(getActivity(), map.get("text"), Toast.LENGTH_LONG).show();
        }
    }
}
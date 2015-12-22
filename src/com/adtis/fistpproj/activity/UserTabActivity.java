package com.adtis.fistpproj.activity;

import android.support.v7.app.ActionBarActivity;
import java.util.ArrayList;

import com.adtis.fistpproj.R;
import com.adtis.fistpproj.util.TabLinearLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import com.adtis.fistpproj.viewpagerindicator.UnderlinePageIndicator;

/**
 * 基于第三方开源控件ViewPagerIndicator的UnderlinePageIndicator，自己写的一个在选项卡底部有衬线的滑动控件。
 * 控件效果图如图所示。 这里面有一个特别的效果是：头部的选项卡在ViewPager切换过程中，底部的滑块也随之动态渐渐滑动过渡。
 *
 * */

public class UserTabActivity extends ActionBarActivity {

    private TabLinearLayout mTabLinearLayout;
    private ArrayList<Fragment> mArryList;
    private ViewPager mPager;

    // 未被选中的选项卡字体颜色
    private int COLOR_NORMAL = Color.DKGRAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usertabbar_activity
        );

        mArryList = new ArrayList<Fragment>();
        // 初始化5个Fragment作为测试。
        for (int i = 0; i < 5; i++) {
            TestFragment f = (TestFragment) TestFragment.newInstance();
            f.id = i;
            mArryList.add(f);
        }

        PagerAdapter mAdapter = new MyFragmentAdapter(
                getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        UnderlinePageIndicator mIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setFades(false);
        mIndicator.setSelectedColor(0xff33B5E5);

        mTabLinearLayout = (TabLinearLayout) findViewById(R.id.tab_LinearLayout);

        ArrayAdapter mTabLinearLayoutAdapter = new MyTabLinearLayoutAdapter(
                this, -1);
        mTabLinearLayout.initialization(mPager, mTabLinearLayoutAdapter,
                mIndicator);
    }

    private class MyTabLinearLayoutAdapter extends ArrayAdapter {
        private Context context;

        public MyTabLinearLayoutAdapter(Context context, int resource) {
            super(context, resource);
            this.context = context;
        }

        @Override
        public int getCount() {
            return mArryList.size();
        }

        // 在这里仅仅返回一个TextView作为选项卡的View。
        // 此处可以返回更丰富的View。
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView v = new TextView(context);
            v.setGravity(Gravity.CENTER);
            v.setText("选项卡" + position);
            v.setTextColor(COLOR_NORMAL);

            return v;
        }
    }

    // 仅仅用于测试的Fragment，用一个id标识。
    public static class TestFragment extends Fragment {

        public int id;

        public static Fragment newInstance() {
            return new TestFragment();
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            TextView v = new TextView(getActivity());
            v.setGravity(Gravity.CENTER);
            v.setTextSize(50f);
            v.setText("Fragment: " + id);

            return v;
        }
    }

    private class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mArryList.get(position);
        }

        @Override
        public int getCount() {
            return mArryList.size();
        }
    }
}
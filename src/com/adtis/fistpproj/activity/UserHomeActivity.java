package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.graphics.Color;
import android.os.Bundle;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.viewpagerindicator.UnderlinePageIndicator;

import java.util.ArrayList;

import static com.adtis.fistpproj.util.CustomToast.DisplayToast;

public class UserHomeActivity extends Activity {

    private final Context context = this;
    private NavActivity usercenter_nav;
    private ImageView usercenter_left;
    private ImageView usercenter_right;

    private LinearLayout mLinearLayout;
    private ArrayList<Fragment> mArryList;
    private ViewPager mPager;
    // 未被选中的选项卡字体颜色
    private int COLOR_NORMAL = Color.DKGRAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        initNav();
    }

    private void initNav() {
        usercenter_nav = (NavActivity) super.findViewById(R.id.nav_usercenter);
        usercenter_nav.setTitle(R.string.usercenter_title);
        usercenter_left = (ImageView) usercenter_nav.findViewById(R.id.iv_nav_back);
        usercenter_right = (ImageView) usercenter_nav.findViewById(R.id.iv_nav_right);
        usercenter_left.setImageResource(R.drawable.iconfont_back);
        usercenter_right.setImageResource(R.drawable.iconfont_send);
        usercenter_nav.setClickCallback(new NavActivity.ClickCallback() {

            @Override
            public void onRightClick() {
                DisplayToast(context, "点击了发送按钮!");
            }

            @Override
            public void onBackClick() {
                Intent intent = new Intent();
                intent.setClass(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mArryList = new ArrayList<Fragment>();
        // 初始化5个Fragment作为测试。
        for (int i = 0; i < 5; i++) {
            TestFragment f = (TestFragment) TestFragment.newInstance();
            f.id = i;
            mArryList.add(f);
        }

        PagerAdapter mAdapter = new MyFragmentAdapter(ActionBarActivity.getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        UnderlinePageIndicator mIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setFades(false);
        mIndicator.setSelectedColor(0xff33B5E5);
        mIndicator
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int pos) {
                        setIndicatorViewSelected(pos);
                    }

                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int arg0) {

                    }
                });

        mLinearLayout = (LinearLayout) findViewById(R.id.tab_LinearLayout);
        // 添加选项卡
        addIndicators();

        // 初始化，第0项被选中
        setIndicatorViewSelected(0);
    }

    // 添加Tab选项卡
    private void addIndicators() {
        for (int i = 0; i < getItemsCount(); i++) {
            View v = getIndicatorAt(i);
            addIndicatorItem(v, i);
        }
    }

    // 在这里设置被选中时候选项卡变化的效果
    private void setIndicatorViewSelected(int pos) {
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            if (i == pos) {
                View v = mLinearLayout.getChildAt(i);
                TextView tv = (TextView) v;
                // Android Holo 样式的蓝色
                tv.setTextColor(0xff33B5E5);
            } else {
                View v = mLinearLayout.getChildAt(i);
                TextView tv = (TextView) v;
                tv.setTextColor(COLOR_NORMAL);
            }
        }
    }

    protected int getItemsCount() {
        return mArryList.size();
    }

    // 在这里仅仅返回一个TextView作为选项卡的View。
    // 此处可以返回更丰富的View。
    protected View getIndicatorAt(int pos) {
        TextView v = new TextView(this);
        v.setGravity(Gravity.CENTER);
        v.setText("选项卡" + pos);
        v.setTextColor(COLOR_NORMAL);
        return v;
    }

    // 在线性布局里面依次添加一个View，为添加的View添加事件。
    private void addIndicatorItem(View view, final int index) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT, 1);
        mLinearLayout.addView(view, index, params);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 当用户点击该View时候，设置ViewPager正确而Pager Item
                set(index);
            }
        });
    }

    private void set(int pos) {
        mPager.setCurrentItem(pos, true);
        setIndicatorViewSelected(pos);
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

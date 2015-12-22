package com.adtis.fistpproj.util;

/**
 * Created by Administrator on 2015/12/22.
 */
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.adtis.fistpproj.viewpagerindicator.PageIndicator;

public class TabLinearLayout extends LinearLayout {

    private int COLOR_NORMAL = Color.DKGRAY;
    private ArrayAdapter mAtapter;
    private ViewPager mPager;
    private TabLinearLayoutListener mTabLinearLayoutListener;

    public TabLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLinearLayout(Context context) {
        super(context);
    }

    public void initialization(ViewPager mPager, ArrayAdapter mAtapter,
                               PageIndicator mPageIndicator) {
        this.mAtapter = mAtapter;
        this.mPager = mPager;

        mPageIndicator
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int pos) {
                        setCurrentItem(pos);

                        if (mTabLinearLayoutListener != null)
                            mTabLinearLayoutListener.onTab(pos);
                    }

                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int arg0) {

                    }
                });

        // 添加选项卡
        addIndicators();

        // 初始化，第0项被选中
        setIndicatorViewSelected(0);
    }

    public void initialization(ViewPager mPager, ArrayAdapter mAtapter,
                               PageIndicator mPageIndicator,
                               TabLinearLayoutListener mTabLinearLayoutListener) {

        this.mTabLinearLayoutListener = mTabLinearLayoutListener;

        initialization(mPager, mAtapter, mPageIndicator);
    }

    // 添加Tab选项卡
    private void addIndicators() {
        for (int i = 0; i < mAtapter.getCount(); i++) {
            View v = mAtapter.getView(i, null, null);
            addIndicatorItem(v, i);
        }
    }

    // 在这里设置被选中时候选项卡变化的效果
    private void setIndicatorViewSelected(int pos) {
        for (int i = 0; i < super.getChildCount(); i++) {
            if (i == pos) {
                View v = super.getChildAt(i);
                TextView tv = (TextView) v;
                // Android Holo 样式的蓝色
                tv.setTextColor(0xff33B5E5);
            } else {
                View v = super.getChildAt(i);
                TextView tv = (TextView) v;
                tv.setTextColor(COLOR_NORMAL);
            }
        }
    }

    // 在线性布局里面依次添加一个View，为添加的View添加事件。
    private void addIndicatorItem(View view, final int index) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT, 1);
        super.addView(view, index, params);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 当用户点击该View时候，设置ViewPager正确而Pager Item
                if (mTabLinearLayoutListener != null)
                    mTabLinearLayoutListener.onTab(v, index);

                // 设置ViewPager的显示项。
                mPager.setCurrentItem(index, true);
                setCurrentItem(index);
            }
        });
    }

    // 设置当前LinearLayout的pos项子view被选中。
    public void setCurrentItem(int pos) {
        setIndicatorViewSelected(pos);
    }

    // 一个接口，当用户点击选项卡时候，方法被回调。
    public interface TabLinearLayoutListener {
        public void onTab(View v, int pos);

        public void onTab(int pos);
    }
}

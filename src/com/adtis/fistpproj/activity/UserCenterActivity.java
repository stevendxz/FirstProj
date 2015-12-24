package com.adtis.fistpproj.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.fragment.*;

import static com.adtis.fistpproj.util.CustomToast.DisplayToast;

public class UserCenterActivity extends FragmentActivity {

    private final Context context = this;
    private NavActivity nav_usercenter;
    private ImageView btn_left;
    private ImageView btn_right;

    /**
     * FragmentTabhost
     */
    private FragmentTabHost mTabHost;

    /**
     * 布局填充器
     */
    private LayoutInflater mLayoutInflater;
    /**
     * Fragment数组界面
     *
     */
    private Class mFragmentArray[] = { Fragment1.class, Fragment2.class,
            Fragment3.class, Fragment4.class};
    /**
     * 选项卡文字
     */
    private String mTextArray[] = { "首页", "消息", "好友", "个人资料" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_activity);
        initView();
    }

    public void initView() {
        /*** 初始化标题栏 */
        nav_usercenter = (NavActivity) super.findViewById(R.id.nav_usercenter);
        nav_usercenter.setTitle(R.string.usercenter_title);
        btn_left = (ImageView) nav_usercenter.findViewById(R.id.iv_nav_back);
        btn_right = (ImageView) nav_usercenter.findViewById(R.id.iv_nav_right);
        btn_left.setImageResource(R.drawable.iconfont_back);
        btn_right.setImageResource(R.drawable.iconfont_send);
        //标题栏中返回按钮和下一步按钮点击事件
        nav_usercenter.setClickCallback(new NavActivity.ClickCallback() {

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

        /*** 初始化用户中心选项卡标签 */
        mLayoutInflater = LayoutInflater.from(this);

        // 找到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 得到fragment的个数
        int count = mTextArray.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i)
                   .setBackgroundResource(R.drawable.tab_btn_select);
        }

    }

    /**
     *
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setBackgroundResource(R.drawable.tab_image_select);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextArray[index]);
        /*textView.setTextColor(getResources().getColor(R.color.gray));
        if(textView.isPressed()) {
            textView.setTextColor(getResources().getColor(R.color.dodgerblue));
        }*/

        return view;
    }

}

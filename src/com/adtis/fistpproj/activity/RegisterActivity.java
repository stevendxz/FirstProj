package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.dao.UserInfoDAO;
import com.adtis.fistpproj.model.UserInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.adtis.fistpproj.util.CustomToast.DisplayToast;

/**
 * Created by Administrator on 2015/12/16.
 */
public class RegisterActivity extends Activity {
    private final Context context = this;
    private NavActivity reg_nav;
    private ImageView btn_left;
    private ImageView btn_right;

    private EditText txt_username;
    private EditText txt_email;
    private EditText txt_pwd;
    private EditText txt_confirmpwd;
    private Button btn_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initRegView();
    }

    public void initRegView() {
        reg_nav = (NavActivity) super.findViewById(R.id.nav_reg);
        reg_nav.setTitle(R.string.reg_title);
        btn_left = (ImageView) reg_nav.findViewById(R.id.iv_nav_back);
        btn_right = (ImageView) reg_nav.findViewById(R.id.iv_nav_right);
        btn_left.setImageResource(R.drawable.iconfont_back);
        btn_right.setImageResource(R.drawable.iconfont_send);
        reg_nav.setClickCallback(new NavActivity.ClickCallback() {

            @Override
            public void onRightClick() {
                DisplayToast(context, "点击了发送按钮!");
            }

            @Override
            public void onBackClick() {
                Intent intent = new Intent();
                intent.setClass(context, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txt_username = (EditText)findViewById(R.id.reg_txt_username);
        txt_email = (EditText)findViewById(R.id.reg_txt_email);
        txt_pwd = (EditText)findViewById(R.id.reg_txt_pwd);
        txt_confirmpwd = (EditText)findViewById(R.id.reg_txt_confirmpwd);
        btn_reg = (Button)findViewById(R.id.btn_reg);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String _username = txt_username.getText().toString();
        String _email = txt_email.getText().toString();
        String _pwd = txt_pwd.getText().toString();
        String _confirmpwd = txt_confirmpwd.getText().toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String _date = df.format(new Date());
        if( _username.equals("")) {
            DisplayToast(context, "用户名不能为空");
            return;
        }
        if(_email.equals("")) {
            DisplayToast(context, "邮箱不能为空");
            return;
        }
        if(_pwd.equals("")) {
            DisplayToast(context, "密码不能为空");
            return;
        }
        if(_confirmpwd.equals("")) {
            DisplayToast(context, "确认密码不能为空");
            return;
        }
        if(!_confirmpwd.equals(_pwd)) {
            DisplayToast(context, "两次输入密码必须相同");
            return;
        }
        Log.v("\nREG_INFO 111::", "\nUSERNAME ==>>\t" + _username +
                "\nEMAIL ==>>\t" + _email +
                "\nPASSWORD ==>>\t" + _pwd +
                "\nCONFIRM PWD ==>>\t" + _confirmpwd +
                "\nDATE ==>>\t" + _date);
        UserInfo info = new UserInfo();
        info.setUsername(_username);
        info.setEmail(_email);
        info.setPassword(_pwd);
        info.setDate(_date);
        UserInfoDAO dao = new UserInfoDAO(context);
        dao.insertUserInfo(info);
        Log.v("MSG", "注册成功");
        DisplayToast(context, "注册成功！！！！");
    }
}

package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.dao.UserInfoDAO;
import com.adtis.fistpproj.model.UserInfo;
import java.util.List;

import static com.adtis.fistpproj.util.CustomToast.DisplayToast;

public class LoginActivity extends Activity {
    private ImageView btn_left;
    private ImageView btn_right;
    private EditText email_edittext;
    private EditText password_edittext;
    private Button btn_login;
    private String email = null;
    private String password = null;

    private TextView btn_forgetpwd;
    private TextView btn_signin;

    final Context context = this;
    private NavActivity navgativeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initLoginView();
    }

    public void initLoginView() {
        navgativeView = (NavActivity) super.findViewById(R.id.nav_login);
        navgativeView.setTitle(R.string.login_title);
        btn_left = (ImageView) navgativeView.findViewById(R.id.iv_nav_back);
        btn_right = (ImageView) navgativeView.findViewById(R.id.iv_nav_right);
        btn_left.setImageResource(R.drawable.iconfont_back);
        btn_right.setImageResource(R.drawable.iconfont_send);
        navgativeView.setClickCallback(new NavActivity.ClickCallback() {

            @Override
            public void onRightClick() {
                DisplayToast(context, "点击了右侧按钮!");
            }

            @Override
            public void onBackClick() {
                Intent intent = new Intent();
                intent.setClass(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        email_edittext = (EditText) findViewById(R.id.login_txt_email);
        password_edittext = (EditText) findViewById(R.id.login_txt_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btn_forgetpwd = (TextView)findViewById(R.id.btn_forgetpwd);
        btn_signin = (TextView)findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void loginUser() {
        email = email_edittext.getText().toString();
        password = password_edittext.getText().toString();
        if (email.equals("")) {
            DisplayToast(context, "邮箱不能为空");
            return;
        }
        if (password.equals("")) {
            DisplayToast(context, "密码不能为空");
            return;
        }
        Log.v("\nLOGIN_INFO 222::", "\nEMAIL==>>" + email + "\nPASSWORD==>>" + password);
        List<UserInfo> list;
        UserInfoDAO dao = new UserInfoDAO(context);
        list = dao.findByEmailAndPwd(email, password);
        Log.v("\nLOGIN_INFO 333::", list.toString());
        DisplayToast(context, "登录成功！！！！");
    }

}

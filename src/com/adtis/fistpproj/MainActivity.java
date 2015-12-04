package com.adtis.fistpproj;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText emailEditText = null;
    private EditText pwdEditText = null;
    private Button loginButton = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        emailEditText = (EditText) this.findViewById(R.id.login_txt_email);
        pwdEditText = (EditText) this.findViewById(R.id.login_txt_pwd);
        loginButton = (Button) this.findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                DisplayToast("点击了注册按钮");
            }
        });
    }

    public void DisplayToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}

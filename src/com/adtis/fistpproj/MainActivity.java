package com.adtis.fistpproj;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.adtis.fistpproj.R;

public class MainActivity extends Activity {
    private TextView emailTextView = null;
    private TextView pwdTextView = null;
    private Button loginButton = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        emailTextView = (TextView)this.findViewById(R.id.login_txt_email);
        pwdTextView = (TextView)this.findViewById(R.id.login_txt_pwd);
        loginButton = (Button)this.findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new Button.OnClickListener(){
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

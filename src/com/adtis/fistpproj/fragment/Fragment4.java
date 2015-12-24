package com.adtis.fistpproj.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.adtis.fistpproj.R;
import com.adtis.fistpproj.dao.UserInfoDAO;
import com.adtis.fistpproj.model.UserInfo;


public class Fragment4 extends Fragment {

    private Button btn_update;
    private Button btn_save;
    private EditText txt_username;
    private EditText txt_email;
    private EditText txt_phone;
    private EditText txt_date;

    private boolean update_status = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment4, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    public void initView() {
        btn_update = (Button) getActivity().findViewById(R.id.btn_update_userinfo);
        btn_save = (Button) getActivity().findViewById(R.id.btn_save_userinfo);

        txt_email = (EditText) getActivity().findViewById(R.id.center_txt_email);
        txt_username = (EditText) getActivity().findViewById(R.id.center_txt_username);
        txt_phone = (EditText) getActivity().findViewById(R.id.center_txt_phone);
        txt_date = (EditText) getActivity().findViewById(R.id.center_txt_createdatee);

        UserInfo info = getUserInfo();
        Log.v("INFO:", info.toString());
        txt_email.setText(info.getEmail());
        txt_username.setText(info.getUsername());
        txt_phone.setText(info.getPhone());
        txt_date.setText(info.getDate());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!update_status) {
                    txt_email.setEnabled(true);
                    txt_phone.setEnabled(true);

                    txt_email.setBackgroundResource(R.drawable.txt_input_enable);
                    txt_phone.setBackgroundResource(R.drawable.txt_input_enable);

                    btn_update.setText("取消");
                    update_status = true;
                } else {
                    txt_email.setEnabled(false);
                    txt_phone.setEnabled(false);

                    txt_email.setBackgroundResource(R.drawable.txt_input_disable);
                    txt_phone.setBackgroundResource(R.drawable.txt_input_disable);

                    btn_update.setText("修改");
                    update_status = false;
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("STATUS::::", String.valueOf(update_status));
                if (update_status) {
                    String _email = txt_email.getText().toString();
                    String _phone = txt_phone.getText().toString();
                    if (_email.equals("")) {
                        _email = "";
                    }
                    if (_phone.equals("")) {
                        _phone = "";
                    }
                    Log.v("UPDATE::::", _email + "::::" + _phone);
                    UserInfo sinfo = getUserInfo();
                    sinfo.setEmail(_email);
                    sinfo.setPhone(_phone);
                    Log.v("UpdateInfo::::", sinfo.toString());
                    UserInfoDAO dao = new UserInfoDAO(getActivity());
                    dao.updateUserInfo(sinfo);

                    txt_email.setEnabled(false);
                    txt_phone.setEnabled(false);

                    txt_email.setBackgroundResource(R.drawable.txt_input_disable);
                    txt_phone.setBackgroundResource(R.drawable.txt_input_disable);
                    btn_update.setText("修改");

                    update_status = false;
                }
            }
        });
    }

    public UserInfo getUserInfo() {
        UserInfo info = new UserInfo();
        UserInfoDAO dao = new UserInfoDAO(getActivity());
        info = dao.findUserInfoByUserName("admin");
        return info;
    }
}
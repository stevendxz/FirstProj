package com.adtis.fistpproj.impl;

import com.adtis.fistpproj.model.UserInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/12/16.
 */
public interface UserInfoDAOImpl {
    public List<UserInfo> findByEmailAndPwd(String email, String pwd);
    public List<UserInfo> findAllUserInfo();
    public void insertUserInfo(UserInfo info);
    public int getCounts();
}

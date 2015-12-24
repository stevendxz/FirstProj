package com.adtis.fistpproj.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.adtis.fistpproj.impl.UserInfoDAOImpl;
import com.adtis.fistpproj.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/16.
 */
public class UserInfoDAO implements UserInfoDAOImpl {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private static final String INSERT_DEFAULT_SQL = "insert into user(username,email,password) values('admin','admin@admin.com','admin')";
    private static final String INSERT_USER_SQL = "insert into user(username,email,password,date) values(?,?,?,?)";
    private static final String FIND_ALLUSER_SQL = "select * from user";
    private static final String FIND_USER_BY_USERNAME_SQL = "select * from user where username=?";
    private static final String FIND_USER_BY_ID_SQL = "select * from user where _id=?";
    private static final String UPDATE_USER_BY_USERNAME_SQL = "update user set email=?,phone=? where username=?";
    private static final String FIND_LOGIN_SQL = "select * from user where email=? and password=?;";
    private static final String FIND_COUNTS_SQL = "select count(*) from user";

    public UserInfoDAO(Context context) {
        helper = new DBOpenHelper(context);
    }

    @Override
    public List<UserInfo> findByEmailAndPwd(String email, String pwd) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(FIND_LOGIN_SQL, new String[]{
                String.valueOf(email), String.valueOf(pwd)
        });
        List<UserInfo> list = new ArrayList<UserInfo>();
        if (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String _username = cursor.getString(cursor.getColumnIndex("username"));
            String _email = cursor.getString(cursor.getColumnIndex("email"));
            String _password = cursor.getString(cursor.getColumnIndex("password"));
            String _phone = cursor.getString(cursor.getColumnIndex("phone"));
            String _date = cursor.getString(cursor.getColumnIndex("date"));
            Log.v("\nUSERINFO", "\nID ==> " + _id +
                    "\nUSERNAME ==> " + _username +
                    "\nEMAIL ==> " + _email +
                    "\nPASSWORD ==> " + _password +
                    "\nDATE ==> " + _date);
            UserInfo info = new UserInfo(_id, _username, _email, _password, _phone, _date);
            Log.v("USERINFO", info.toString());
            Log.v("\nMSG ", "查询用户信息成功");
            list.add(new UserInfo(_id, _username, _email, _password, _phone, _date));
        }
        return list;
    }

    @Override
    public List<UserInfo> findAllUserInfo() {
        db = helper.getWritableDatabase();
        List<UserInfo> list = new ArrayList<UserInfo>();
        Cursor cursor = db.rawQuery(FIND_ALLUSER_SQL, null);
        if (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String _username = cursor.getString(cursor.getColumnIndex("username"));
            String _email = cursor.getString(cursor.getColumnIndex("email"));
            String _password = cursor.getString(cursor.getColumnIndex("password"));
            String _phone = cursor.getString(cursor.getColumnIndex("phone"));
            String _date = cursor.getString(cursor.getColumnIndex("date"));
            Log.v("\nUSERINFO ", "\nID ==> " + _id +
                    "\nUSERNAME ==> " + _username +
                    "\nEMAIL ==> " + _email +
                    "\nPASSWORD ==> " + _password +
                    "\nDATE ==> " + _date);
            UserInfo info = new UserInfo(_id, _username, _email, _password, _phone, _date);
            Log.v("USERINFO", info.toString());
            Log.v("\nMSG ", "查询用户信息成功");
            list.add(new UserInfo(_id, _username, _email, _password, _phone, _date));
        }
        return list;
    }

    @Override
    public UserInfo findUserInfoByID(int id) {
        db = helper.getWritableDatabase();
        UserInfo info = new UserInfo();
        Cursor cursor = db.rawQuery(FIND_USER_BY_ID_SQL, new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String _username = cursor.getString(cursor.getColumnIndex("username"));
            String _phone = cursor.getString(cursor.getColumnIndex("phone"));
            String _email = cursor.getString(cursor.getColumnIndex("email"));
            String _password = cursor.getString(cursor.getColumnIndex("password"));
            String _date = cursor.getString(cursor.getColumnIndex("date"));
            Log.v("\nUSERINFO ", "\nID ==> " + _id +
                    "\nUSERNAME ==> " + _username +
                    "\nEMAIL ==> " + _email +
                    "\nPASSWORD ==> " + _password +
                    "\nDATE ==> " + _date);
            Log.v("\nMSG ", "查询用户信息成功");
            info = new UserInfo(_id, _username, _email, _password, _phone, _date);
        }
        return info;
    }

    @Override
    public UserInfo findUserInfoByUserName(String username) {
        db = helper.getWritableDatabase();
        UserInfo info = new UserInfo();
        Cursor cursor = db.rawQuery(FIND_USER_BY_USERNAME_SQL, new String[]{String.valueOf(username)});
        if (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String _username = cursor.getString(cursor.getColumnIndex("username"));
            String _email = cursor.getString(cursor.getColumnIndex("email"));
            String _password = cursor.getString(cursor.getColumnIndex("password"));
            String _phone = cursor.getString(cursor.getColumnIndex("phone"));
            String _date = cursor.getString(cursor.getColumnIndex("date"));
            Log.v("\nUSERINFO ", "\nID ==> " + _id +
                    "\nUSERNAME ==> " + _username +
                    "\nEMAIL ==> " + _email +
                    "\nPASSWORD ==> " + _password +
                    "\nDATE ==> " + _date);
            Log.v("\nMSG ", "查询用户信息成功");
            info = new UserInfo(_id, _username, _email, _password, _phone,  _date);
        }
        return info;
    }

    @Override
    public void insertUserInfo(UserInfo info) {
        db = helper.getWritableDatabase();
        db.execSQL(INSERT_USER_SQL,new String[]{
                info.getUsername(),
                info.getEmail(),
                info.getPassword(),
                info.getDate()
        });
        Log.v("\nMSG ", "添加用户信息成功");
    }

    @Override
    public void updateUserInfo(UserInfo info) {
        db = helper.getWritableDatabase();
        db.execSQL(UPDATE_USER_BY_USERNAME_SQL,new String[]{
                info.getEmail(),
                info.getPhone(),
                info.getUsername()
        });
        Log.v("\nMSG ", "更新用户信息成功");
    }

    @Override
    public int getCounts() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(FIND_COUNTS_SQL, null);
        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        }
        return 0;
    }
}

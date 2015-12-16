package com.adtis.fistpproj.data;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    /*
   * 关闭数据库连接
   */
    public void closeConn(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * 打开数据库连接
     */
    public Connection openConnection() {
        Properties prop = new Properties();
        String driver = null;
        String url = null;
        String username = null;
        String password = null;

        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream(
                    "DBConfig.properties"));

            driver = prop.getProperty("driver");
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");

            Log.v("DBUtil::::","\nDriver==>"+driver+"\nURL==>"+url+"\nUserName==>"+username+"\nPWD==>"+password);

            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

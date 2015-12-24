package com.adtis.fistpproj.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DBNAME = "mydb.db";

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "test_user";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_DATE = "date";
    }

    //private static final String CREATE_SQL = "create table user(_id integer primary key autoincrement,username varchar(16)," +
    //        "email varchar(255),password varchar(64),date varchar(255))";
    private static final String COMMA_SEP = ",";
    private static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_USERNAME + "VARCHAR(16)" + COMMA_SEP +
                    FeedEntry.COLUMN_EMAIL + "VARCHAR(255)" + COMMA_SEP +
                    FeedEntry.COLUMN_PASSWORD + "VARCHAR(16)" + COMMA_SEP +
                    FeedEntry.COLUMN_PHONE + "VARCHAR(255)" + COMMA_SEP +
                    FeedEntry.COLUMN_DATE + "VARCHAR(255)" + COMMA_SEP +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

        //继承SQLiteOpenHelper类的类必须有自己的构造函数
    //该构造函数4个参数，直接调用父类的构造函数。其中第一个参数为该类本身；第二个参数为数据库的名字；
    //第3个参数是用来设置游标对象的，这里一般设置为null；参数四是数据库的版本号。
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.getWritableDatabase();
    }

    //该构造函数有3个参数，因为它把上面函数的第3个参数固定为null了
    public DBHelper(Context context, String name, int verson){
        this(context, name, null, verson);
    }

    //该构造函数只有2个参数，在上面函数 的基础山将版本号固定了
    public DBHelper(Context context, String name){
        this(context, name, VERSION);
    }

    //该构造函数只有1个参数
    public DBHelper(Context context){
        this(context, DBNAME, null, VERSION);
    }

    public void onClose() {
        this.getWritableDatabase().close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+FeedEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
        //super.onDowngrade(db, oldVersion, newVersion);
    }

    /** 添加用户表信息 */
    public void addUserInfo(String username, String email, String password, String date) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.FeedEntry.COLUMN_USERNAME, username);
        values.put(DBHelper.FeedEntry.COLUMN_EMAIL, email);
        values.put(DBHelper.FeedEntry.COLUMN_PASSWORD, password);
        values.put(DBHelper.FeedEntry.COLUMN_DATE, date);
        this.getWritableDatabase().insert(
                DBHelper.FeedEntry.TABLE_NAME, DBHelper.FeedEntry.COLUMN_ID, values);
    }
    /** 更新用户表信息 */
    public void updateUserInfo(int id, String username, String email, String password) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.FeedEntry.COLUMN_USERNAME, username);
        values.put(DBHelper.FeedEntry.COLUMN_EMAIL, email);
        values.put(DBHelper.FeedEntry.COLUMN_PASSWORD, password);

        String selection = FeedEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        this.getWritableDatabase().update(FeedEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    /** 删除用户信息 */
    public void delUserInfoByID(int id) {
        this.getWritableDatabase().delete(
                DBHelper.FeedEntry.TABLE_NAME, DBHelper.FeedEntry.COLUMN_ID + " = " + id, null);
    }
    /** 清空清湖信息 */
    public void delAllUserInfo() {
        this.getWritableDatabase().delete(
                DBHelper.FeedEntry.TABLE_NAME, null, null);
    }
}

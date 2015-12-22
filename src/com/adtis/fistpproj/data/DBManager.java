package com.adtis.fistpproj.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.adtis.fistpproj.dao.DBOpenHelper;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

/**
 * sqlite的dao操作帮助类
 *
 * @author weisir
 *
 */

public class DBManager {

    public static final MapRowProcessor MAPROWPROCESSOR = new MapRowProcessor();

    private String tableName;    //表名
    private String[] cols;        //列名
    private SQLiteDatabase database;
    private DBOpenHelper helper;

    public DBManager(String tableName, String[] columns) {
        this.tableName = tableName;
        this.cols = columns;
    }

    public List<Map<String, String>> query(String sqlWhere) {
        return query(sqlWhere,null);
    }

    public List<Map<String, String>> query(String sqlWhere,String[] sqlWhereArgs) {
        List<Map<String, String>> list = new LinkedList<Map<String, String>>();

        Cursor cursor = null;
        try {
            database = helper.getWritableDatabase();
            cursor = database.query(tableName, cols, sqlWhere, sqlWhereArgs, null, null, null);

            while (cursor.moveToNext()) {
                list.add(MAPROWPROCESSOR.process(cursor));
            }
        } catch (Exception e) {
            Log.e("DAOHelper", "插入失败");
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return list;
    }

    public int insert(List<Map<String, String>> list) {
        database = helper.getWritableDatabase();

        try {
            // 打开数据库
            database.beginTransaction();
            for (Map<String, String> map : list) {
                ContentValues v = mapToContentValues(map);
                database.insert(tableName, null, v);
                if (v != null) {
                    v.clear();
                    v = null;
                }
            }
            // 设置事务成功.
            database.setTransactionSuccessful();

            return list.size();
        } catch (Exception e) {
            Log.e("DAOHelper", "插入失败");
            return -1;
        } finally {
            database.endTransaction();
        }

    }

    public int delete(String sqlWhere) {
        database = helper.getWritableDatabase();
        try {
            // 打开数据库
            database.beginTransaction();
            // 设置事务成功.
            int rowCount = database.delete(tableName, sqlWhere, null);
            database.setTransactionSuccessful();
            return rowCount;
        } catch (Exception e) {
            Log.e("DAOHelper", "删除失败");
            return -1;
        } finally {
            database.endTransaction();
        }
    }

    private ContentValues mapToContentValues(Map<String, String> map) {

        ContentValues values = new ContentValues();
        for (String col : cols) {
            values.put(col, map.get(col));
        }

        return values;
    }

    static public void clear(List<Map<String, String>> list) {
        if (null == list) {
            return;
        }
        for (Map<String, String> map : list) {
            if (null != map) {
                map.clear();
            }
        }
        list.clear();
    }

    /**
     * 查询得到列表
     *
     * @param sql
     *            　完整的select语句，可包含?，但不能用;结尾
     * @param selectionArgs
     *            　查询参数
     * @param rp
     *            　　每行的处理，可使用DAOHelper.MAPROWPROCESSOR
     * @return
     */
    static public <T> List<T> query(String sql, String[] selectionArgs, RowProcessor<T> rp) {
        List<T> list = new LinkedList<T>();

        Cursor c = null;
        try {
            SQLiteDatabase database;
            DBOpenHelper helper = null;
            database = helper.getWritableDatabase();

            c = database.rawQuery(sql, selectionArgs);

            while (c.moveToNext()) {
                list.add(rp.process(c));
            }
        } catch (Exception e) {
            Log.e("DAOHelper", "查询失败");

        } finally {

            if (null != c) {
                c.close();
            }
        }

        return list;
    }

    //行处理接口
    public interface RowProcessor<T> {
        T process(Cursor c);
    }

    //将每行处理成Map<String,String>结构
    static public class MapRowProcessor implements RowProcessor<Map<String,String>> {

        @Override
        public Map<String,String> process(Cursor c) {

            Map<String,String> map = new HashMap<String,String>();

            String[] columns = c.getColumnNames();

            for (String col : columns) {
                map.put(col, c.getString(c.getColumnIndex(col)));
            }

            return map;
        }

    }
}
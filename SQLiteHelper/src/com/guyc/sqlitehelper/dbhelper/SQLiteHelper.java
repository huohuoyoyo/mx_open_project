package com.guyc.sqlitehelper.dbhelper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 * Created by PQ on 2015/6/1.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "database.db";//数据库名
    private static final int DB_VERSION = 1;            //版本号
    private static SQLiteHelper sSQLiteHelper;

    public static final String TABLE_NAME = "person_info";//数据库表名
    public static final String P_ID = "_id";               //id
    public static final String P_NAME = "name";            //名字
    public static final String P_AGE = "age";              //年龄
    public static final String P_SEX = "sex";              //性别

    private static final String PT_PRIMARY = "PRIMARY KEY AUTOINCREMENT";//主键
    private static final String PT_INTEGER = "INTEGER";                     //integer类型
    private static final String PT_TEXT = "TEXT";                           //text类型

    private static final String PTN_INTEGER = "INTEGER NOT NULL";
    private static final String PTN_TEXT = "TEXT NOT NULL";

    public static synchronized SQLiteHelper getInstance(Context context) {
        if (sSQLiteHelper == null) {
            synchronized (SQLiteHelper.class) {
                if (sSQLiteHelper == null) {
                    sSQLiteHelper = new SQLiteHelper(context);
                }
            }
        }
        return sSQLiteHelper;
    }

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /************语句1***************/
        String TABLE_PERSON = "CREATE TABLE IF NOT EXISTS" + " " + TABLE_NAME + "(" + P_ID + " " + PT_INTEGER + " " + PT_PRIMARY + ","
                + P_NAME + " " + PT_TEXT + ","
                + P_AGE + " " + PT_INTEGER + ","
                + P_SEX + " " + PT_TEXT + ")";

        /************语句2***************/
        /*
        String TABLE_PERSON = "CREATE TABLE IF NOT EXISTS" + TABLE_NAME + "( _id integer primary key autoincrement, name text not null,age integer, sex text)';
        */

        db.execSQL(TABLE_PERSON);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String TABLE_PERSON = "DROP TABLE IF EXISTS" + TABLE_NAME;
        db.execSQL(TABLE_PERSON);
        onCreate(db);

    }


}

package com.guyc.sqlitehelper.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.guyc.sqlitehelper.Utils.PQLog;
import com.guyc.sqlitehelper.module.Person;

import java.util.ArrayList;

/**
 * 数据库管理类
 * Created by PQ on 2015/6/1.
 */
public class DbManager {

    private static DbManager sDbManager;
    private static SQLiteHelper sSQLiteHelper;
    private SQLiteDatabase mDb;

    public DbManager(Context context) throws SQLiteException {
        sSQLiteHelper = SQLiteHelper.getInstance(context);
    }

    public static DbManager getInstance(Context context) {
        if (sDbManager == null) {
            synchronized (DbManager.class) {
                if (sDbManager == null) {
                    sDbManager = new DbManager(context);
                }
            }
        }
        return sDbManager;
    }

    //保存数据
    public synchronized void saveData(Person person) {
        if (sSQLiteHelper == null) {
            PQLog.e(PQLog.getTag(), "mSQLiteHelper is null");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.P_NAME, person.getName());
        values.put(SQLiteHelper.P_AGE, person.getAge());
        values.put(SQLiteHelper.P_SEX, person.getSex());
        PQLog.e(PQLog.getTag(), "is exist :" + isExists(person.getName()));

        if (!isExists(person.getName())) {//insert data

            PQLog.e(PQLog.getTag(), "insert ------");
            mDb = sSQLiteHelper.getWritableDatabase();
            /*****************java 语句******************/
            /*******语句1******/
            String selection = "(" + SQLiteHelper.P_NAME + "," + SQLiteHelper.P_AGE + "," + SQLiteHelper.P_SEX + ")" + "values(?,?,?)";
            /*******语句2******/
            //String selection = "(name,age,sex)" ;
            /*******语句3******/
            // String selection = "values(?,?,?)";
            mDb.insert(SQLiteHelper.TABLE_NAME, selection, values);

            /*****************sql 语句*******************/
            /*mDb.execSQL("insert into " + TABLE_NAME + "(name,age,sex)" + "values(?,?,?)",
                new Object[]{person.getName(), person.getAge(), person.getSex()});*/

        } else {//更新数据

            PQLog.e(PQLog.getTag(), "replace ------");
            String selection = SQLiteHelper.P_NAME + "=?";
            //String selection = "name"+"=?";
            mDb.update(SQLiteHelper.TABLE_NAME, values, selection, new String[]{person.getName()});
        }
        mDb.close();
    }

    //查找所有数据
    public synchronized ArrayList<Person> getPersons() {
        if (sSQLiteHelper == null) {
            PQLog.e(PQLog.getTag(), "mSQLiteHelper is null");
            return null;
        }
        mDb = sSQLiteHelper.getReadableDatabase();
        /***********语句1************/
        //Cursor cursor = mDb.rawQuery("select * from " + TABLE_NAME, new String[]{});

        /***********语句2************/
        //如果第二个参数为null，则查找所有列的数据
        Cursor cursor = mDb.query(SQLiteHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor == null || cursor.getColumnCount() == 0) {
            PQLog.e(PQLog.getTag(), "cursor is :" + cursor);
            return null;
        }
        PQLog.e(PQLog.getTag(), "size: " + cursor.getCount());
        ArrayList<Person> persons = new ArrayList<>();
        while (cursor.moveToNext()) {
            Person p = new Person();
            p.setName(cursor.getString(1));
            p.setAge(cursor.getString(2));
            p.setSex(cursor.getString(3));
            persons.add(p);
        }
        mDb.close();
        cursor.close();
        return persons;
    }

    //删除数据
    public synchronized boolean deleteData(String name) {
        if (sSQLiteHelper == null) {
            PQLog.e(PQLog.getTag(), "mSQLiteHelper is null");
            return false;
        }
        int del;
        mDb = sSQLiteHelper.getWritableDatabase();
        String selection = SQLiteHelper.P_NAME + "=?";//条件
        del = mDb.delete(SQLiteHelper.TABLE_NAME, selection, new String[]{name});
        mDb.close();
        return del > 0;
    }

    //更新数据
    public synchronized void updateData(Person p) {
        if (sSQLiteHelper == null || p == null) {
            PQLog.e(PQLog.getTag(), "mSQLiteHelper is null");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.P_NAME, p.getName());
        values.put(SQLiteHelper.P_AGE, p.getAge());
        values.put(SQLiteHelper.P_SEX, p.getSex());
        mDb = sSQLiteHelper.getWritableDatabase();
        String selection = SQLiteHelper.P_NAME + "=?";//条件
        mDb.update(SQLiteHelper.TABLE_NAME, values, selection, new String[]{p.getName()});
        mDb.close();
    }

    //判断数据库中是否存在name相同的数据
    public synchronized boolean isExists(String name) {
        if (sSQLiteHelper == null) {
            return false;
        }
        mDb = sSQLiteHelper.getReadableDatabase();
        String selection = SQLiteHelper.P_NAME + "=?";
        Cursor cursor = mDb.query(SQLiteHelper.TABLE_NAME, new String[]{SQLiteHelper.P_NAME}, selection, new String[]{name}, null, null, null);
        PQLog.e(PQLog.getTag(), "cursor: " + cursor + " size: " + cursor.getCount());
        boolean b = (cursor.getCount() > 0);
        cursor.close();
        return b;
    }

}

package com.guyc.sqlitehelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.guyc.sqlitehelper.Utils.PQLog;
import com.guyc.sqlitehelper.dbhelper.DbManager;
import com.guyc.sqlitehelper.module.MyAdapter;
import com.guyc.sqlitehelper.module.Person;

import java.util.ArrayList;

public class MyActivity extends Activity implements View.OnClickListener {
    private EditText mEtName, mEtAge, mEtSex;
    private Button mBtnAdd, mBtnDel, mBtnGet;
    private ListView mListView;
    private MyAdapter mMyAdapter;
    private ArrayList<Person> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mList = new ArrayList<>();
        mMyAdapter = new MyAdapter(this, mList);
        mListView = (ListView) findViewById(R.id.lv);
        mEtName = (EditText) findViewById(R.id.name);
        mEtAge = (EditText) findViewById(R.id.age);
        mEtSex = (EditText) findViewById(R.id.sex);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnDel = (Button) findViewById(R.id.btn_del);
        mBtnGet = (Button) findViewById(R.id.btn_get);

        mListView.setAdapter(mMyAdapter);
        mBtnAdd.setOnClickListener(this);
        mBtnDel.setOnClickListener(this);
        mBtnGet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String name = mEtName.getText().toString();
        String sex = mEtSex.getText().toString();
        String age = mEtAge.getText().toString();
        switch (v.getId()) {
            case R.id.btn_add:
                if (name.equals("")) {
                    Toast.makeText(MyActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                Person person = new Person();
                person.setName(name);
                person.setSex(sex);
                person.setAge(age);
                DbManager.getInstance(MyActivity.this).saveData(person);
                mMyAdapter.setData(DbManager.getInstance(MyActivity.this).getPersons());
                break;
            case R.id.btn_del:
                if (name.equals("")) {
                    Toast.makeText(MyActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean del = DbManager.getInstance(MyActivity.this).deleteData(name);
                if (del) {
                    Toast.makeText(MyActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    mMyAdapter.setData(DbManager.getInstance(MyActivity.this).getPersons());
                }
                break;
            case R.id.btn_get:
                if (mList != null) {
                    mList.clear();
                }
                mList = DbManager.getInstance(MyActivity.this).getPersons();
                PQLog.e(PQLog.getTag(), "list size: " + mList.size());
                mMyAdapter.setData(mList);
                break;
        }

    }
}

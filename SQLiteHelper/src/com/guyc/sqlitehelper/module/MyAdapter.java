package com.guyc.sqlitehelper.module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.guyc.sqlitehelper.R;

import java.util.ArrayList;

/**
 * 适配器
 * Created by MX on 2015/6/1.
 */
public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Person> mList;

    public MyAdapter(Context mContext, ArrayList<Person> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(ArrayList<Person> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null || mList.size() == 0 ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null || mList.size() == 0 ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_item, parent, false);
            viewHolder.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.mTvAge = (TextView) convertView.findViewById(R.id.tv_age);
            viewHolder.mTvSex = (TextView) convertView.findViewById(R.id.tv_sex);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvName.setText(mList.get(position).getName());
        viewHolder.mTvAge.setText(mList.get(position).getAge() + "");
        viewHolder.mTvSex.setText(mList.get(position).getSex());

        return convertView;
    }


    class ViewHolder {
        TextView mTvName;
        TextView mTvAge;
        TextView mTvSex;
    }
}

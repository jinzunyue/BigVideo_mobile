package com.pbtd.mobile.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqinchao on 17/4/27.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {
    public Context mContext;
    public List<T> mDatas;

    public BaseListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mDatas==null?0:mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDatas(List<T> list) {
        mDatas = list;
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void appendDatas(List<T> list) {
        if (mDatas == null) mDatas = new ArrayList<T>();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void clearDatas() {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        if (mDatas == null || position < 0 || position > mDatas.size()-1)
            return;
        mDatas.remove(position);
        notifyDataSetChanged();
    }
}

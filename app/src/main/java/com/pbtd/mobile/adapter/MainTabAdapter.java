package com.pbtd.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pbtd.mobile.fragment.BaseFragment;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class MainTabAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mDatas;

    public MainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas==null?0:mDatas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        BaseFragment fragment = mDatas.get(position);
        return fragment.getTitle();
    }

    public void setData(List<BaseFragment> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }
}

package com.pbtd.mobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.pbtd.mobile.activity.BaseActivity;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class BaseFragment extends Fragment {

    public String mTitle = "标题";
    public BaseActivity mActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}

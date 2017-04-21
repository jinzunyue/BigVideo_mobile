package com.pbtd.mobile.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class BaseFragment extends Fragment {

    public String mTitle = "标题";

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}

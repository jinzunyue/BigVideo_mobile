package com.pbtd.mobile.fragment.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pbtd.mobile.R;
import com.pbtd.mobile.fragment.BaseFragment;

/**
 * Created by xuqinchao on 17/5/11.
 */

public class FragmentProgram extends BaseFragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = "节目单";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_program, null);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }


}
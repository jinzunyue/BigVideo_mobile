package com.pbtd.mobile.fragment.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pbtd.mobile.R;
import com.pbtd.mobile.adapter.LiveLeftAdapter;
import com.pbtd.mobile.adapter.LiveRightAdapter;
import com.pbtd.mobile.fragment.BaseFragment;
import com.pbtd.mobile.widget.FixListView;

/**
 * Created by xuqinchao on 17/5/11.
 */

public class FragmentChannel extends BaseFragment{

    private FixListView mRightGridView;
    private FixListView mLeftGridView;
    private LiveRightAdapter mRightAdapter;
    private LiveLeftAdapter mLeftAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = "电视台";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_channel, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLeftGridView = (FixListView) view.findViewById(R.id.list_left);
        mRightGridView = (FixListView) view.findViewById(R.id.list_right);
        mLeftAdapter = new LiveLeftAdapter(mActivity);
        mLeftGridView.setAdapter(mLeftAdapter);
        mRightAdapter = new LiveRightAdapter(mActivity);
        mRightGridView.setAdapter(mRightAdapter);
    }
}

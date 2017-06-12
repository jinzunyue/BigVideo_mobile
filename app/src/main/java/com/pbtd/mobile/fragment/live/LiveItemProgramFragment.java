package com.pbtd.mobile.fragment.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pbtd.mobile.adapter.LiveProgramAdapter;
import com.pbtd.mobile.fragment.BaseFragment;
import com.pbtd.mobile.model.live.WeekProgramModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/6/8.
 */

public class LiveItemProgramFragment extends BaseFragment{

    private ListView mListView;
    private List<WeekProgramModel> mModelList;
    private LiveProgramAdapter mAdapter;
    private int mRightCurrentPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mListView = new ListView(mActivity);
        initView();
        return mListView;
    }

    private void initView() {
        mAdapter = new LiveProgramAdapter(mActivity);
        mListView.setAdapter(mAdapter);
        mAdapter.setDatas(mModelList);

        if ("今天".equals(getTitle())) {
            for (int i = 0; i < mModelList.size(); i++) {
                WeekProgramModel weekProgramModel = mModelList.get(i);
                if (weekProgramModel.getStartTime()*1000 > System.currentTimeMillis())
                    mRightCurrentPosition = i==(mModelList.size()-1)?(mModelList.size()-1):(i+1);
            }
            mAdapter.setCurrentSelectPosition(mRightCurrentPosition);
            mListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mListView.smoothScrollToPosition(mRightCurrentPosition);
                }
            }, 300);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    public void setProgramData(List<WeekProgramModel> modelList) {
        mModelList = modelList;

    }


}

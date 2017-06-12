package com.pbtd.mobile.fragment.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pbtd.mobile.adapter.LiveRightAdapter;
import com.pbtd.mobile.fragment.BaseFragment;
import com.pbtd.mobile.model.live.CategoryInnerModel;
import com.pbtd.mobile.model.temp.TempLiveModel;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.widget.EPGPoP;

/**
 * Created by xuqinchao on 17/6/8.
 */

public class LiveItemChannelFragment extends BaseFragment{

    private ListView mListView;
    private LiveRightAdapter mAdapter;
    private TempLiveModel mModel;
    private EPGPoP mEpgPop;
    private Listener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mListView = new ListView(mActivity);
        initView();
        return mListView;
    }

    private void initView() {
        mAdapter = new LiveRightAdapter(mActivity);
        mListView.setAdapter(mAdapter);
        mAdapter.setDatas(mModel.modelList);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryInnerModel categoryInnerModel = mAdapter.getDatas().get(position);
                if (mEpgPop == null)
                    mEpgPop = new EPGPoP(mActivity);
                mEpgPop.setData(categoryInnerModel);
                mEpgPop.setListener(new EPGPoP.Listener() {
                    @Override
                    public void onBackUrl(String backUrl) {
                        if (mListener != null)
                            mListener.onUrlChanged(backUrl);
                    }
                });
                mEpgPop.showAtLocation(mListView, Gravity.TOP, 0, UIUtil.convertDpToPixel(mActivity, 235));
            }
        });
    }

    public void setChannelData(TempLiveModel model) {
        mModel = model;
    }

    public interface Listener{
        void onUrlChanged(String url);
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }
}

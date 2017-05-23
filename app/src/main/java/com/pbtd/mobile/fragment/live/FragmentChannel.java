package com.pbtd.mobile.fragment.live;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pbtd.mobile.Constants;
import com.pbtd.mobile.R;
import com.pbtd.mobile.adapter.LiveLeftAdapter;
import com.pbtd.mobile.adapter.LiveRightAdapter;
import com.pbtd.mobile.fragment.BaseFragment;
import com.pbtd.mobile.model.live.CategoryInnerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqinchao on 17/5/11.
 */

public class FragmentChannel extends BaseFragment{

    private ListView mRightGridView;
    private ListView mLeftGridView;
    private LiveRightAdapter mRightAdapter;
    private LiveLeftAdapter mLeftAdapter;

    private int mLeftCurrentPosition;
    private int mRightCurrentPosition;
    private Listener mListener;
    private View mRootView;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            LiveLeftAdapter.ViewHolder viewHolder = (LiveLeftAdapter.ViewHolder) mLeftGridView.getChildAt(0).getTag();
            viewHolder.mTv.setSelected(true);

            LiveRightAdapter.ViewHolder viewHolder2 = (LiveRightAdapter.ViewHolder) mRightGridView.getChildAt(0).getTag();
            viewHolder2.mTitle.setSelected(true);
            viewHolder2.mPlay.setSelected(true);
            viewHolder2.mNow.setSelected(true);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = "电视台";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_live_channel, null);
        initView();
        return mRootView;
    }

    private void initView() {
        mLeftGridView = (ListView) mRootView.findViewById(R.id.list_left);
        mRightGridView = (ListView) mRootView.findViewById(R.id.list_right);
        mLeftAdapter = new LiveLeftAdapter(mActivity, false);
        mLeftGridView.setAdapter(mLeftAdapter);
        mRightAdapter = new LiveRightAdapter(mActivity);
        mRightGridView.setAdapter(mRightAdapter);

        mLeftGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LiveLeftAdapter.ViewHolder viewHolder = (LiveLeftAdapter.ViewHolder) mLeftGridView.getChildAt(mLeftCurrentPosition).getTag();
                viewHolder.mTv.setSelected(false);
                LiveLeftAdapter.ViewHolder viewHolder2 = (LiveLeftAdapter.ViewHolder) mLeftGridView.getChildAt(position).getTag();
                viewHolder2.mTv.setSelected(true);
                mLeftCurrentPosition = position;
            }
        });

        mRightGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LiveRightAdapter.ViewHolder viewHolder2 = (LiveRightAdapter.ViewHolder) mRightGridView.getChildAt(mRightCurrentPosition).getTag();
                viewHolder2.mTitle.setSelected(false);
                viewHolder2.mPlay.setSelected(false);
                viewHolder2.mNow.setSelected(false);

                LiveRightAdapter.ViewHolder viewHolder = (LiveRightAdapter.ViewHolder) mRightGridView.getChildAt(position).getTag();
                viewHolder.mTitle.setSelected(true);
                viewHolder.mPlay.setSelected(true);
                viewHolder.mNow.setSelected(true);
                mRightCurrentPosition = position;

                if (mListener != null) {
                    mListener.onClick(Constants.CCTV_9, mRightAdapter.getDatas().get(position).getVideoId()+"");
                }
            }
        });

    }

    public void setData(List<CategoryInnerModel> list) {

        if (mLeftAdapter==null || mRightAdapter==null) {
            return;
        }

        List<String> leftData = new ArrayList<>();
        leftData.add("央视");
        leftData.add("卫视");
        mLeftAdapter.setDatas(leftData);

        mRightAdapter.setDatas(list);

        mHandler.sendEmptyMessage(0);
        mListener.onClick(Constants.CCTV_9, list.get(0).getVideoId()+"");
    }

    public interface Listener {
        void onClick(String url, String videoId);
    }
    public void setListener(Listener listener) {
        mListener = listener;
    }
}

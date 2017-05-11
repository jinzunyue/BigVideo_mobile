package com.pbtd.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.PlayLandActivity;
import com.pbtd.mobile.adapter.MainTabAdapter;
import com.pbtd.mobile.fragment.live.FragmentChannel;
import com.pbtd.mobile.fragment.live.FragmentProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class LiveVideoFragment extends BaseFragment {

    private VideoView mVideoView;
    private int mCurrentPlayPosition;
    private String mCurrentUrl;
    private ProgressBar mProgress;
    private ViewPager mViewPager;
    private MainTabAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_video, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mProgress = (ProgressBar) view.findViewById(R.id.pb);
        mVideoView = (VideoView) view.findViewById(R.id.video_view);
        ImageView mFullScreenView = (ImageView) view.findViewById(R.id.iv_full_screen);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        mVideoView.setOnErrorListener((mp, what, extra) -> true);
        mVideoView.seekTo(mCurrentPlayPosition);
        mVideoView.setOnPreparedListener((mp) -> {
            mProgress.setVisibility(View.GONE);
            mp.start();
        });

        mFullScreenView.setOnClickListener((full_view) -> {
            mCurrentPlayPosition = mVideoView.getCurrentPosition();
            Intent intent = new Intent(mActivity, PlayLandActivity.class);
            intent.putExtra(PlayLandActivity.URL, mCurrentUrl);
            intent.putExtra(PlayLandActivity.TITLE, "测试标题");
            intent.putExtra(PlayLandActivity.PROGRESS_POSITION, mCurrentPlayPosition);
            startActivityForResult(intent, 1);
        });

        mAdapter = new MainTabAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);

        FragmentChannel fragmentChannel = new FragmentChannel();
        FragmentProgram fragmentProgram = new FragmentProgram();

        List<BaseFragment> list = new ArrayList<>();
        list.add(fragmentChannel);
        list.add(fragmentProgram);
        mAdapter.setData(list);

        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCurrentPlayPosition = data.getIntExtra(PlayLandActivity.PROGRESS_POSITION, 0);
        mProgress.setVisibility(View.VISIBLE);
        mVideoView.seekTo(mCurrentPlayPosition);
    }

}

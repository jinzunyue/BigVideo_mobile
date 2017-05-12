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
import android.widget.TextView;
import android.widget.VideoView;

import com.pbtd.mobile.Constants;
import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.PlayLandActivity;
import com.pbtd.mobile.adapter.MainTabAdapter;
import com.pbtd.mobile.fragment.live.FragmentChannel;
import com.pbtd.mobile.fragment.live.FragmentProgram;
import com.pbtd.mobile.model.live.CategoryInnerModel;
import com.pbtd.mobile.presenter.live.LiveContract;
import com.pbtd.mobile.presenter.live.LivePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class LiveVideoFragment extends BaseFragment implements LiveContract.View{

    private VideoView mVideoView;
    private int mCurrentPlayPosition;
    private ProgressBar mProgress;
    private ViewPager mViewPager;
    private MainTabAdapter mAdapter;
    private LivePresenter mPresenter;
    private FragmentChannel mFragmentChannel;
    private FragmentProgram mFragmentProgram;
    private TextView mTvChannel;
    private TextView mTvProgram;
    private View mTvChannelIndicator;
    private View mTvProgramIndicator;

    private String mCurrentUrl;
    private String mCurrentVideoId;

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
        mTvChannel = (TextView) view.findViewById(R.id.tv_channel);
        mTvProgram = (TextView) view.findViewById(R.id.tv_program);
        mTvChannelIndicator = view.findViewById(R.id.view_channel);
        mTvProgramIndicator = view.findViewById(R.id.view_program);

        mVideoView.setOnErrorListener((mp, what, extra) -> true);
        mVideoView.seekTo(mCurrentPlayPosition);
        mVideoView.setOnPreparedListener((mp) -> {
            mProgress.setVisibility(View.GONE);
            mp.start();
        });

        mFullScreenView.setOnClickListener((full_view) -> {
            mCurrentPlayPosition = mVideoView.getCurrentPosition();
            Intent intent = new Intent(mActivity, PlayLandActivity.class);
            intent.putExtra(PlayLandActivity.URL, Constants.CCTV_7);
            intent.putExtra(PlayLandActivity.TITLE, "测试标题");
            intent.putExtra(PlayLandActivity.PROGRESS_POSITION, mCurrentPlayPosition);
            startActivityForResult(intent, 1);
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchIndicator(position==0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTvProgram.setOnClickListener(v -> mViewPager.setCurrentItem(1));
        mTvChannel.setOnClickListener(v -> mViewPager.setCurrentItem(0));

        mAdapter = new MainTabAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mFragmentChannel = new FragmentChannel();
        mFragmentProgram = new FragmentProgram();
        mFragmentChannel.setListener(new FragmentChannel.Listener() {
            @Override
            public void onClick(String url, String videoId) {
                mVideoView.setVideoPath(url);
                mCurrentUrl = url;
                mCurrentVideoId = videoId;
            }
        });

        List<BaseFragment> list = new ArrayList<>();
        list.add(mFragmentChannel);
        list.add(mFragmentProgram);
        mAdapter.setData(list);

        mProgress.setVisibility(View.VISIBLE);
        switchIndicator(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new LivePresenter(mActivity, this);
        mPresenter.getCategoryList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCurrentPlayPosition = data.getIntExtra(PlayLandActivity.PROGRESS_POSITION, 0);
        mProgress.setVisibility(View.VISIBLE);
        mVideoView.seekTo(mCurrentPlayPosition);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showCategoryList(List<CategoryInnerModel> list) {
        mFragmentChannel.setData(list);
    }

    private void switchIndicator(boolean isChannel) {
        mTvChannel.setSelected(isChannel);
        mTvChannelIndicator.setSelected(isChannel);
        mTvProgram.setSelected(!isChannel);
        mTvProgramIndicator.setSelected(!isChannel);
    }

}

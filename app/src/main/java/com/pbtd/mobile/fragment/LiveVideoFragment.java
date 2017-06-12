package com.pbtd.mobile.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
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
import com.pbtd.mobile.fragment.live.LiveItemChannelFragment;
import com.pbtd.mobile.model.live.CategoryInnerModel;
import com.pbtd.mobile.model.live.WeekProgramModel;
import com.pbtd.mobile.model.temp.TempLiveModel;
import com.pbtd.mobile.presenter.live.LiveContract;
import com.pbtd.mobile.presenter.live.LivePresenter;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class LiveVideoFragment extends BaseFragment implements LiveContract.View, LiveItemChannelFragment.Listener {

    private VideoView mVideoView;
    private int mCurrentPlayPosition;
    private ProgressBar mProgress;
    private ViewPager mViewPager;
    private MainTabAdapter mAdapter;
    private LivePresenter mPresenter;

    private String mCurrentUrl;
    private String mCurrentVideoId;
    private PagerSlidingTabStrip mIndicator;
    private TextView mCollection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_video, null);
        initView(view);
        return view;
    }

    boolean isFragment1 = true;
    private void initView(View view) {
        mProgress = (ProgressBar) view.findViewById(R.id.pb);
        mVideoView = (VideoView) view.findViewById(R.id.video_view);
        ImageView mFullScreenView = (ImageView) view.findViewById(R.id.iv_full_screen);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mCollection = (TextView) view.findViewById(R.id.tv_collection);
        mIndicator = (PagerSlidingTabStrip) view.findViewById(R.id.indicator);
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });
        mVideoView.seekTo(mCurrentPlayPosition);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mProgress.setVisibility(View.GONE);
                mp.start();
            }
        });
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mProgress.setVisibility(View.VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mProgress.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });

        mFullScreenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPlayPosition = mVideoView.getCurrentPosition();
                Intent intent = new Intent(mActivity, PlayLandActivity.class);
                intent.putExtra(PlayLandActivity.URL, Constants.CCTV_7);
                intent.putExtra(PlayLandActivity.TITLE, "测试标题");
                intent.putExtra(PlayLandActivity.PROGRESS_POSITION, mCurrentPlayPosition);
                startActivityForResult(intent, 1);
            }
        });

        mCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(mActivity, "我的收藏");
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mAdapter = new MainTabAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new LivePresenter(mActivity, this);
        mPresenter.getCategoryList();
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (hidden && mVideoView.isPlaying()) {
//            mCurrentPlayPosition = mVideoView.getCurrentPosition();
//            mVideoView.pause();
//            Logger.i(Constants.LOGGER_TAG, "hidden");
//        } else {
//            mVideoView.setVideoPath(mCurrentUrl);
//            mVideoView.seekTo(mCurrentPlayPosition);
//            Logger.i(Constants.LOGGER_TAG, "show");
//
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (mVideoView.isPlaying()) {
//            mCurrentPlayPosition = mVideoView.getCurrentPosition();
//            mVideoView.pause();
//            Logger.i(Constants.LOGGER_TAG, "onPause");
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (!TextUtils.isEmpty(mCurrentUrl)) {
//            mVideoView.setVideoPath(mCurrentUrl);
//            mVideoView.seekTo(mCurrentPlayPosition);
//            Logger.i(Constants.LOGGER_TAG, "show");
//        }
//    }

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
        if (list != null) {
            CategoryInnerModel categoryInnerModel = list.get(0);
            mCurrentVideoId = categoryInnerModel.getVideoId()+"";

//            mPresenter.getProgramOfWeek(mCurrentVideoId);
        }

        List<TempLiveModel> modelList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            TempLiveModel model = new TempLiveModel();
            model.modelList = list;
            model.typeName = "分类" + i;
            modelList.add(model);
        }

        modelList.get(0).typeName = "央视";
        modelList.get(1).typeName = "卫视";
        modelList.get(2).typeName = "本地";
        modelList.get(3).typeName = "特色";
        modelList.get(4).typeName = "影视";
        modelList.get(5).typeName = "体育";

        List<BaseFragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < modelList.size(); i++) {
            LiveItemChannelFragment fragment = new LiveItemChannelFragment();
            fragment.setTitle(modelList.get(i).typeName);
            fragment.setChannelData(modelList.get(i));
            fragment.setListener(this);
            fragmentList.add(fragment);
        }

        mAdapter.setData(fragmentList);
        mIndicator.setViewPager(mViewPager);

        mVideoView.setVideoPath(Constants.CCTV_9);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgramWeek(List<WeekProgramModel> list) {
//        if (mFragmentProgram != null)
//            mFragmentProgram.setDatas(list);
    }

    @Override
    public void onUrlChanged(String url) {
        mVideoView.setVideoPath(url);
        mProgress.setVisibility(View.VISIBLE);
    }
}

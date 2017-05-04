package com.pbtd.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.pbtd.mobile.Constants;
import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.PlayLandActivity;
import com.pbtd.mobile.adapter.LiveLeftAdapter;
import com.pbtd.mobile.adapter.LiveRightAdapter;
import com.pbtd.mobile.model.temp.LiveRightModel;
import com.pbtd.mobile.widget.FixListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class LiveVideoFragment extends BaseFragment {

    private FixListView mRightGridView;
    private FixListView mLeftGridView;
    private LiveLeftAdapter mLeftAdapter;

    private int mLeftSelectPosition;
    private int mRightSelectPosition;
    private LiveRightAdapter mRightAdapter;
    private VideoView mVideoView;
    private int mCurrentPlayPosition;
    private String mCurrentUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_video, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mVideoView = (VideoView) view.findViewById(R.id.video_view);
        ImageView mFullScreenView = (ImageView) view.findViewById(R.id.iv_full_screen);
        mLeftGridView = (FixListView) view.findViewById(R.id.list_left);
        mRightGridView = (FixListView) view.findViewById(R.id.list_right);

        mLeftAdapter = new LiveLeftAdapter(mActivity);
        mLeftGridView.setAdapter(mLeftAdapter);
        mRightAdapter = new LiveRightAdapter(mActivity);
        mRightGridView.setAdapter(mRightAdapter);

        mVideoView.setOnErrorListener((mp, what, extra) -> false);
        mVideoView.setMediaController(new MediaController(mActivity));
        mVideoView.setOnPreparedListener((mp) -> {
            mp.start();
            mp.seekTo(mCurrentPlayPosition);
        });

        mFullScreenView.setOnClickListener((full_view) -> {
            mCurrentPlayPosition = mVideoView.getCurrentPosition();
            Intent intent = new Intent(mActivity, PlayLandActivity.class);
            intent.putExtra(PlayLandActivity.URL, mCurrentUrl);
            intent.putExtra(PlayLandActivity.POSITION, mCurrentPlayPosition);
            startActivityForResult(intent, 1);
        });

        List<String> mLeftDatas = new ArrayList<>();
        mLeftDatas.add("央视");
        mLeftDatas.add("卫视");
        mLeftDatas.add("本地");
        mLeftDatas.add("特色");
        mLeftDatas.add("影视");
        mLeftDatas.add("体育");
        mLeftAdapter.setDatas(mLeftDatas);

        List<LiveRightModel> mRightDatas = new ArrayList<>();
        mRightDatas.add(new LiveRightModel("卫视", Constants.BTV_WEISHI));
        mRightDatas.add(new LiveRightModel("科教", Constants.BTV_KEJIAO));
        mRightDatas.add(new LiveRightModel("文艺", Constants.BTV_WENYI));
        mRightDatas.add(new LiveRightModel("影视", Constants.BTV_YINGSHI));
        mRightDatas.add(new LiveRightModel("财经", Constants.BTV_CAIJING));
        mRightDatas.add(new LiveRightModel("青年", Constants.BTV_QINGNIAN));
        mRightDatas.add(new LiveRightModel("体育", Constants.BTV_TIYU));
        mRightAdapter.setDatas(mRightDatas);

        mLeftGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLeftGridView.getChildAt(mLeftSelectPosition).setSelected(false);
                mLeftSelectPosition = position;
                mLeftGridView.getChildAt(position).setSelected(true);
            }
        });
        mRightGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mRightGridView.getChildAt(mRightSelectPosition).setSelected(false);
                mRightSelectPosition = position;
                mRightGridView.getChildAt(position).setSelected(true);

                mCurrentUrl = mRightAdapter.getDatas().get(position).url;
                mVideoView.setVideoPath(mCurrentUrl);
            }
        });

        mCurrentUrl = mRightDatas.get(0).url;
        mVideoView.setVideoPath(mCurrentUrl);
        mVideoView.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCurrentPlayPosition = data.getIntExtra(PlayLandActivity.POSITION, 0);
        mVideoView.seekTo(mCurrentPlayPosition);
        mVideoView.start();
    }

    //    @Override
//    public void onResume() {
//        super.onResume();
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVideoView.getLayoutParams();
//        if (mActivity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            layoutParams.height = UIUtil.convertDpToPixel(mActivity, 210);
//            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//        } else {
//            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//            layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
//        }
//        mCurrentPlayPosition = mVideoView.getCurrentPosition();
//        mVideoView.setLayoutParams(layoutParams);
//    }
}

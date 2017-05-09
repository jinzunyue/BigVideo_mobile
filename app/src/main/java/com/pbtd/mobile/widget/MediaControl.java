package com.pbtd.mobile.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.pbtd.mobile.Constants;
import com.pbtd.mobile.R;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by xuqinchao on 17/5/8.
 */

public class MediaControl {

    public static final int FULL_SCREEN = 0;

    private Formatter mFormatter;
    private Context mContext;
    private VideoView mVideoView;
    private View mRootView;
    private ImageView mPlay;
    private TextView mCurrentTime;
    private TextView mEndTime;
    private SeekBar mProgress;
    private boolean mDragging;

    private static final int sDefaultTimeout = 3000;
    private boolean mShowing;
    StringBuilder mFormatBuilder;
    private ImageView mFullScreen;
    private OnClickListener mListener;

    public MediaControl(Context context, VideoView videoView) {
        if (videoView == null) {
            Log.e(Constants.GLOBAL_TAG, "videoView can not be null");
            return;
        }
        
        mContext = context;
        mRootView = View.inflate(context, R.layout.custom_media_control_port, null);
        mVideoView = videoView;

        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        
        initView();
    }

    private void initView() {
        mPlay = (ImageView) mRootView.findViewById(R.id.iv_play);
        mCurrentTime = (TextView) mRootView.findViewById(R.id.tv_start);
        mEndTime = (TextView) mRootView.findViewById(R.id.tv_end);
        mFullScreen = (ImageView) mRootView.findViewById(R.id.iv_full_screen);
        mProgress = (SeekBar) mRootView.findViewById(R.id.seek_bar);
        
        mProgress.setMax(1000);
        mProgress.setOnSeekBarChangeListener(mSeekListener);
        
        mPlay.setOnClickListener(v -> {
            if (mVideoView.isPlaying()) {
                pause();
            } else {
                play();
            } 
        });

        mFullScreen.setOnClickListener(v -> {
            if (mListener != null)
                mListener.onClick(FULL_SCREEN);
        });
    }

    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStartTrackingTouch(SeekBar bar) {
            show(3600000);

            mDragging = true;

            mRootView.removeCallbacks(mShowProgress);
        }

        @Override
        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser) {
                return;
            }

            long duration = mVideoView.getDuration();
            long newposition = (duration * progress) / 1000L;
            mVideoView.seekTo( (int) newposition);
            if (mCurrentTime != null)
                mCurrentTime.setText(stringForTime( (int) newposition));
        }

        @Override
        public void onStopTrackingTouch(SeekBar bar) {
            mDragging = false;
            setProgress();
            mPlay.setSelected(mVideoView.isPlaying());// TODO: 17/5/8 测试是否正确 
            show(sDefaultTimeout);

            mRootView.post(mShowProgress);
        }
    };

    private final Runnable mFadeOut = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private final Runnable mShowProgress = new Runnable() {
        @Override
        public void run() {
            int pos = setProgress();
            if (!mDragging && mShowing && mVideoView.isPlaying()) {
                mRootView.postDelayed(mShowProgress, 1000 - (pos % 1000));
            }
        }
    };

    private final View.OnClickListener mRewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = mVideoView.getCurrentPosition();
            pos -= 5000; // milliseconds
            mVideoView.seekTo(pos);
            setProgress();

            show(sDefaultTimeout);
        }
    };

    private final View.OnClickListener mFfwdListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = mVideoView.getCurrentPosition();
            pos += 15000; // milliseconds
            mVideoView.seekTo(pos);
            setProgress();

            show(sDefaultTimeout);
        }
    };

    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    private int setProgress() {
        if (mVideoView == null || mDragging) {
            return 0;
        }
        int position = mVideoView.getCurrentPosition();
        int duration = mVideoView.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 1000L * position / duration;
                mProgress.setProgress( (int) pos);
            }
            int percent = mVideoView.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        mEndTime.setText(stringForTime(duration));
        mCurrentTime.setText(stringForTime(position));

        return position;
    }

    public void show() {
        show(sDefaultTimeout);
    }

    public void show(int timeout) {
        mRootView.setVisibility(View.VISIBLE);

        if (!mShowing) {
            setProgress();
            mShowing = true;
        }
        mPlay.setSelected(mVideoView.isPlaying());// TODO: 17/5/8 测试正确

        mRootView.post(mShowProgress);

        if (timeout != 0) {
            mRootView.removeCallbacks(mFadeOut);
            mRootView.postDelayed(mFadeOut, timeout);
        }
    }

    public void play() {
        mPlay.setSelected(true);
        mVideoView.start();
    }

    public void pause() {
        mPlay.setSelected(false);
        mVideoView.pause();
    }

    public boolean isShowing() {
        return mShowing;
    }

    public void hide() {
        if (mShowing) {
            mRootView.removeCallbacks(mShowProgress);
            mRootView.setVisibility(View.GONE);
            mShowing = false;
        }
    }
    
    public void setVideoView(VideoView videoView) {
        if (videoView == null) {
            Log.e(Constants.GLOBAL_TAG, "videoView can not be null");
            return;
        }
        mVideoView = videoView;
    }
    
    public View getRootView() {
        return mRootView;
    }

    public void setOnClickListener(OnClickListener listener) {
        if (listener != null)
            mListener = listener;
    }
    public interface OnClickListener {
        void onClick(int action);
    }
}

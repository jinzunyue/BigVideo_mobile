package com.pbtd.mobile.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.pbtd.mobile.Constants;
import com.pbtd.mobile.R;
import com.pbtd.mobile.model.ProductDetailModel;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.widget.FullMediaControl;
import com.pbtd.mobile.widget.FullScreenPoP;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PlayLandActivity extends BaseActivity {
    private static final int FadeOutTime = 3000;
    public static final String PROGRESS_POSITION = "position";
    public static final String PRODUCT_DETAIL = "product_list";
    public static final String CURRENT_ITEM = "current_item";
    public static final String URL = "url";//直播使用  temp
    public static final String TITLE = "title";//直播使用 temp

    private String mUrl;
    private String mTitle;//直播使用  temp

    private int mProgressPosition;
    private int mCurrentItem;
    private List<ProductDetailModel> mProductDetailModelList;

    private boolean mIsControlDisplay;
    private boolean mIsLocked;
    private VideoView mVideoView;
    private TextView mTitleView;
    private ImageView mShareView;
    private RelativeLayout mControlTop;
    private ImageView mCutScreenView;
    private ImageView mLockView;
    private ImageView mBigScreenView;
    private ImageView mCollectView;
    private FullMediaControl mControl;
    private ImageView mCenterPlayView;
    private RelativeLayout mRoot;
    private FullScreenPoP mSelectPop;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_land);

        initView();

    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        mProgressPosition = mIntent.getIntExtra(PROGRESS_POSITION, 0);
        mCurrentItem = mIntent.getIntExtra(CURRENT_ITEM, 0);
        mProductDetailModelList = mIntent.getParcelableArrayListExtra(PRODUCT_DETAIL);

        mTitle = mIntent.getStringExtra(TITLE);
        mUrl = mIntent.getStringExtra(URL);
    }

    @Override
    protected void initView() {
        mRoot = (RelativeLayout) findViewById(R.id.rl_root);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        ImageView mBackView = (ImageView) findViewById(R.id.iv_back);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mTitleView = (TextView) findViewById(R.id.tv_title);
        mShareView = (ImageView) findViewById(R.id.iv_share);
        mCenterPlayView = (ImageView) findViewById(R.id.iv_center_play);
        mCollectView = (ImageView) findViewById(R.id.iv_collect);
        mBigScreenView = (ImageView) findViewById(R.id.iv_big_screen);
        mLockView = (ImageView) findViewById(R.id.iv_lock);
        mCutScreenView = (ImageView) findViewById(R.id.iv_cut_screen);
        mControlTop = (RelativeLayout) findViewById(R.id.rl_control_top);

        mControl = new FullMediaControl(this, mVideoView);
        mControl.setListener(new FullMediaControl.Listener() {
            @Override
            public void onClick(int click_type) {
                switch (click_type) {
                    case FullMediaControl.TYPE:
                        UIUtil.showToast(PlayLandActivity.this, "高清切换成功");
                        break;
                    case FullMediaControl.SELECT:
                        if (mSelectPop==null) {
                            mSelectPop = new FullScreenPoP(PlayLandActivity.this, 20);
                        }
                        mSelectPop.showAtLocation(mVideoView, Gravity.RIGHT, 0, 0);
                        break;
                }
            }
        });
        View controlView = mControl.getRootView();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, UIUtil.convertDpToPixel(this, 57));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mRoot.addView(controlView, layoutParams);

        if (mProductDetailModelList != null) {
            try {
                ProductDetailModel productDetailModel = mProductDetailModelList.get(mCurrentItem);
                String movieList = productDetailModel.getMovieList();
//                JSONArray jsonArray = new JSONArray(movieList);
//                JSONObject jsonObject = jsonArray.getJSONObject(0);
//                String movieurl = jsonObject.getString("movieurl");
//                mVideoView.setVideoPath(movieurl);
                mVideoView.setVideoPath(Constants.TEMP_PLAY_URL);
                mTitleView.setText(productDetailModel.getSeriesName());

                JSONObject jsonObject = new JSONObject("{}");
            } catch (JSONException e) {
                e.printStackTrace();
                UIUtil.showToast(this, "播放异常");
                return;
            }
        } else {
            mTitleView.setText(mTitle);
//            mVideoView.setVideoPath(mUrl);
            mVideoView.setVideoPath(Constants.TEMP_PLAY_URL);
        }


        mVideoView.seekTo(mProgressPosition);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                mp.start();
                mControl.show();
                mRoot.removeCallbacks(mFadeOut);
                mRoot.postDelayed(mFadeOut, FadeOutTime);
            }
        });
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });

        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mIsLocked) {
                            mLockView.setVisibility(View.VISIBLE);
                            mRoot.removeCallbacks(mFadeOut);
                            mRoot.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mLockView.setVisibility(View.GONE);
                                }
                            }, FadeOutTime);
                        } else {
                            if (mIsControlDisplay) {
                                hideAll();
                            } else {
                                showAll();
                                mRoot.removeCallbacks(mFadeOut);
                                mRoot.postDelayed(mFadeOut, FadeOutTime);
                            }
                            mIsControlDisplay = !mIsControlDisplay;
                        }
                        return true;
                }
                return false;
            }
        });

        mShareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(PlayLandActivity.this, "分享成功");
                mRoot.removeCallbacks(mFadeOut);
                mRoot.postDelayed(mFadeOut, FadeOutTime);
            }
        });
        mCollectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(PlayLandActivity.this, "收藏成功");
                mRoot.removeCallbacks(mFadeOut);
                mRoot.postDelayed(mFadeOut, FadeOutTime);
            }
        });
        mBigScreenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(PlayLandActivity.this, "大屏推送成功");
                mRoot.removeCallbacks(mFadeOut);
                mRoot.postDelayed(mFadeOut, FadeOutTime);
            }
        });
        mLockView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsLocked) {
                    showAll();
                } else {
                    hideAll();
                    mLockView.setVisibility(View.VISIBLE);
                }
                mLockView.setVisibility(View.VISIBLE);
                mIsLocked = !mIsLocked;
                mLockView.setSelected(!mLockView.isSelected());
                mRoot.removeCallbacks(mFadeOut);
                mRoot.postDelayed(mFadeOut, FadeOutTime);
            }
        });
        mCenterPlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControl.show();
                if (mVideoView.isPlaying()) {
                    mControl.pause();
                } else {
                    mControl.play();
                }
                mCenterPlayView.setSelected(!mCenterPlayView.isSelected());
                mRoot.removeCallbacks(mFadeOut);
                mRoot.postDelayed(mFadeOut, FadeOutTime);
            }
        });
        mCutScreenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                mRoot.removeCallbacks(mFadeOut);
                screenshot();
            }
        });
        mControl.getRootView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mRoot.removeCallbacks(mFadeOut);
                mRoot.postDelayed(mFadeOut, FadeOutTime);
                return true;
            }
        });
        mControlTop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mRoot.removeCallbacks(mFadeOut);
                mRoot.postDelayed(mFadeOut, FadeOutTime);
                return true;
            }
        });
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(PROGRESS_POSITION, mVideoView.getCurrentPosition());
                setResult(RESULT_OK, intent);
                PlayLandActivity.this.finish();
            }
        });

    }

    private final Runnable mFadeOut = new Runnable() {
        @Override
        public void run() {
            hideAll();
        }
    };

    private void showAll() {
        mControlTop.setVisibility(View.VISIBLE);
        mLockView.setVisibility(View.VISIBLE);
        mCenterPlayView.setVisibility(View.VISIBLE);
        mCutScreenView.setVisibility(View.VISIBLE);
        mControl.show();
    }

    private void hideAll() {
        mControlTop.setVisibility(View.GONE);
        mLockView.setVisibility(View.GONE);
        mCenterPlayView.setVisibility(View.GONE);
        mCutScreenView.setVisibility(View.GONE);
        mControl.hide();
    }

    private void screenshot() {
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            try {
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                String filePath = sdCardPath + "/BigVideo" +System.currentTimeMillis() + ".png";

                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                UIUtil.showToast(this, "截屏成功");
            } catch (Exception e) {
                e.printStackTrace();
                UIUtil.showToast(this, "截屏失败");
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.putExtra(PROGRESS_POSITION, mVideoView.getCurrentPosition());
            setResult(RESULT_OK, intent);
            PlayLandActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

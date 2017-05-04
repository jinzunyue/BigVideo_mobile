package com.pbtd.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.adapter.TempTabAdapter;
import com.pbtd.mobile.model.PlayInfoModel;
import com.pbtd.mobile.model.ProductInfoListModel;
import com.pbtd.mobile.presenter.play.PlayContract;
import com.pbtd.mobile.presenter.play.PlayPresenter;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.widget.FixGridView;

import java.util.List;

public class PlayActivity extends BaseActivity implements PlayContract.View{

    public static final String PRODUCT_CODE = "PRODUCT_CODE";
    public static final String PACKAGE_CODE = "PACKAGE_CODE";

    private String mProduct_code = "";
    private String mPackage_code = "";
    private PlayPresenter mPresenter;
    private VideoView mVideoView;
    private RadioGroup mSelectView;
    private TextView mNameView;
    private TextView mTypeView;
    private FixGridView mRelativeView;
    private TempTabAdapter mAdapter;
    private ScrollView mScrollView;
    private static int mCurrentPosition;
    private MediaController mMediaController;
    private String mCurrentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mProduct_code = getIntent().getStringExtra(PRODUCT_CODE);
        mPackage_code = getIntent().getStringExtra(PACKAGE_CODE);
        mPresenter = new PlayPresenter(this, this);
        mPresenter.getPlayInfo(mProduct_code);
        mPresenter.getRelativeProductInfoList(mPackage_code, mProduct_code, "6", "1");
        initVideoView();
    }

    private void initVideoView() {
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        RelativeLayout mTopView = (RelativeLayout) findViewById(R.id.rl_top);
        ImageView backView = (ImageView) findViewById(R.id.iv_back);
        ImageView fullScreenView = (ImageView) findViewById(R.id.iv_full_screen);
        ImageView mDesciptionButtonView = (ImageView) findViewById(R.id.iv_more);
        TextView mDescriptionView = (TextView) findViewById(R.id.tv_description);
        mNameView = (TextView) findViewById(R.id.tv_name);
        mTypeView = (TextView) findViewById(R.id.tv_type);
        mSelectView = (RadioGroup) findViewById(R.id.ll_selected);
        mRelativeView = (FixGridView) findViewById(R.id.lv_recommend);

        mVideoView = (VideoView) findViewById(R.id.video_view);
        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setOnPreparedListener((mp) -> {
            mp.start();
            mVideoView.seekTo(mCurrentPosition);
        });

        mAdapter = new TempTabAdapter(this, false);
        mRelativeView.setAdapter(mAdapter);

        backView.setOnClickListener((v -> this.finish()));

        mDesciptionButtonView.setOnClickListener((description_view) -> {
            mDescriptionView.setVisibility(mDescriptionView.getVisibility()== View.VISIBLE?View.GONE:View.VISIBLE);
        });

        fullScreenView.setOnClickListener((full_view) -> {

            mCurrentPosition = mVideoView.getCurrentPosition();
            Intent intent = new Intent(this, PlayLandActivity.class);
            intent.putExtra(PlayLandActivity.URL, mCurrentUrl);
            intent.putExtra(PlayLandActivity.POSITION, mCurrentPosition);
            startActivityForResult(intent, 1);

//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVideoView.getLayoutParams();
//            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                layoutParams.height = UIUtil.convertDpToPixel(this, 210);
//                layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//                mScrollView.setVisibility(View.VISIBLE);
//            } else {
//                layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//                layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                mScrollView.setVisibility(View.GONE);
//            }
//            mCurrentPosition = mVideoView.getCurrentPosition();
//            mVideoView.setLayoutParams(layoutParams);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCurrentPosition = getIntent().getIntExtra(PlayLandActivity.POSITION, 0);
        mVideoView.seekTo(mCurrentPosition);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVideoView.getLayoutParams();
//        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
//            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//            mScrollView.setVisibility(View.GONE);
//        } else {
//            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//            layoutParams.height = UIUtil.convertDpToPixel(this, 210);
//            mScrollView.setVisibility(View.VISIBLE);
//        }
//        mVideoView.setLayoutParams(layoutParams);
//    }

    @Override
    public void showPlayInfo(PlayInfoModel playInfoModel) {
        List<PlayInfoModel.ProgramInfoModel> programList = playInfoModel.programList;
        mCurrentUrl = programList.get(0).movieList.get(0).movieUrl;
        mVideoView.setVideoPath(mCurrentUrl);
        mVideoView.start();

        if ( programList != null) {
            mSelectView.removeAllViews();
            for (int i = 0; i < programList.size(); i++) {
                RadioButton textView = new RadioButton(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(UIUtil.convertDpToPixel(this, 55), UIUtil.convertDpToPixel(this, 50));
                layoutParams.rightMargin = UIUtil.convertDpToPixel(this, 10);
                textView.setText(i+"");
                textView.setTextSize(14);
                textView.setTag(i);
                textView.setGravity(Gravity.CENTER);
                textView.setButtonDrawable(null);
                textView.setBackgroundDrawable(null);
                textView.setBackgroundResource(R.drawable.select_movielist);
                textView.setLayoutParams(layoutParams);
                mSelectView.addView(textView);

                textView.setOnClickListener((v) -> {
                    int tag = (int) v.getTag();
                    mNameView.setText(programList.get(tag).programName + "");
                    mCurrentUrl = programList.get(0).movieList.get(0).movieUrl;
                    mVideoView.setVideoPath(mCurrentUrl);
                    mVideoView.start();
                });

                mNameView.setText(programList.get(0).programName+"");
                textView.setSelected(i==0);
            }
        }
    }

    @Override
    public void showRelativeProductInfoList(ProductInfoListModel productInfoListModel) {
        if (productInfoListModel.listInfo != null) {
            while (productInfoListModel.listInfo.size() < 6) {
                productInfoListModel.listInfo.addAll(productInfoListModel.listInfo);
            }

            while (productInfoListModel.listInfo.size() > 6) {
                productInfoListModel.listInfo.remove(productInfoListModel.listInfo.size() - 1);
            }
        }
        mAdapter.setDatas(productInfoListModel.listInfo);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

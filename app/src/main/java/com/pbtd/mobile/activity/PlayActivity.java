package com.pbtd.mobile.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.pbtd.mobile.Constants;
import com.pbtd.mobile.R;
import com.pbtd.mobile.adapter.TempTabAdapter;
import com.pbtd.mobile.model.ProductDetailModel;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.presenter.play.PlayContract;
import com.pbtd.mobile.presenter.play.PlayPresenter;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.widget.FixGridView;
import com.pbtd.mobile.widget.MediaControl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayActivity extends BaseActivity implements PlayContract.View {

    public static final String PRODUCT_CODE = "PRODUCT_CODE";
    private int mCurrentPosition;
    private int mCurrentItem;

    private String mProduct_code = "";
    private PlayContract.Presenter mPresenter;
    private VideoView mVideoView;
    private RelativeLayout mSelectView;
    private TextView mNameView;
    private TextView mTypeView;
    private FixGridView mRelativeView;
    private TempTabAdapter mAdapter;
    private ScrollView mScrollView;
    private String mCurrentUrl;
    private TextView mDirectorView;
    private TextView mActorView;
    private TextView mYearView;
    private TextView mDescriptionView;
    private RelativeLayout mDetailView;
    private TextView mSelectTitleView;
    private LinearLayout mSelectContainer;
    private TextView mUpdateView;
    private int mCurrentSelectPosition;//当前选择的第几集
    private List<ProductDetailModel> mModelList;//当前数据集
    private MediaControl mControl;
    private RelativeLayout mTop;
    private ProgressBar mProgress;
    private ImageView mDesciptionButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initView();

        mPresenter = new PlayPresenter(this, this);
        mPresenter.getProductDetail(mProduct_code);
        mPresenter.getRelativeProductList(mProduct_code);
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        mProduct_code = mIntent.getStringExtra(PRODUCT_CODE);
    }

    @Override
    protected void initView() {
        mProgress = (ProgressBar) findViewById(R.id.pb);
        mTop = (RelativeLayout) findViewById(R.id.rl_top);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        ImageView backView = (ImageView) findViewById(R.id.iv_back);
        mDesciptionButtonView = (ImageView) findViewById(R.id.iv_more);
        mDescriptionView = (TextView) findViewById(R.id.tv_description);
        mNameView = (TextView) findViewById(R.id.tv_name);
        mTypeView = (TextView) findViewById(R.id.tv_type);
        mDirectorView = (TextView) findViewById(R.id.tv_director);
        mActorView = (TextView) findViewById(R.id.tv_actor);
        mYearView = (TextView) findViewById(R.id.tv_year);
        mUpdateView = (TextView) findViewById(R.id.tv_update);
        mSelectTitleView = (TextView) findViewById(R.id.tv_select_title);
        mSelectContainer = (LinearLayout) findViewById(R.id.ll_select_container);
        mDetailView = (RelativeLayout) findViewById(R.id.rl_detail);
        mSelectView = (RelativeLayout) findViewById(R.id.rl_select);
        mRelativeView = (FixGridView) findViewById(R.id.lv_recommend);

        mVideoView = (VideoView) findViewById(R.id.video_view);
        mControl = new MediaControl(this, mVideoView);
        View rootView = mControl.getRootView();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.convertDpToPixel(this, 42));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mTop.addView(rootView, layoutParams);
        mControl.setOnClickListener(new MediaControl.OnClickListener() {
            @Override
            public void onClick(int action) {
                mCurrentPosition = mVideoView.getCurrentPosition();
                Intent intent = new Intent(PlayActivity.this, PlayLandActivity.class);
                intent.putExtra(PlayLandActivity.PROGRESS_POSITION, mCurrentPosition);
                intent.putExtra(PlayLandActivity.CURRENT_ITEM, mCurrentSelectPosition);
                intent.putParcelableArrayListExtra(PlayLandActivity .PRODUCT_DETAIL, (ArrayList) mModelList);
                startActivityForResult(intent, 1);
            }
        });

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mProgress.setVisibility(View.GONE);
                mp.start();
                mControl.show();
            }
        });

        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mControl.isShowing()) {
                        mControl.hide();
                    }else {
                        mControl.show();
                    }
                    return true;
                }
                return PlayActivity.super.onTouchEvent(event);
            }
        });

        mAdapter = new TempTabAdapter(this, false);
        mRelativeView.setAdapter(mAdapter);
        mRelativeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductModel productModel = mAdapter.getDatas().get(position);
                String seriesCode = productModel.getSeriesCode();
                Intent intent = new Intent(PlayActivity.this, PlayActivity.class);
                intent.putExtra(PlayActivity.PRODUCT_CODE, seriesCode);
                PlayActivity.this.startActivity(intent);
            }
        });

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayActivity.this.finish();
            }
        });

        mDesciptionButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailView.setVisibility(mDetailView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                mDesciptionButtonView.setSelected(mDetailView.getVisibility() == View.VISIBLE);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        mProduct_code = intent.getStringExtra(PRODUCT_CODE);
        mPresenter.getProductDetail(mProduct_code);
        mPresenter.getRelativeProductList(mProduct_code);
        super.onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCurrentPosition = data.getIntExtra(PlayLandActivity.PROGRESS_POSITION, 0);
        mCurrentSelectPosition = data.getIntExtra(PlayLandActivity.CURRENT_ITEM, 0);
        ProductDetailModel productDetailModel = mModelList.get(mCurrentSelectPosition);
        String movieList = productDetailModel.getMovieList();
        try {
//            JSONArray jsonArray = new JSONArray(movieList);
//            JSONObject jsonObject = jsonArray.getJSONObject(0);
//            String movieurl = jsonObject.getString("movieurl");
            mProgress.setVisibility(View.VISIBLE);
//            mVideoView.setVideoPath(movieurl);
            mVideoView.setVideoPath(Constants.TEMP_PLAY_URL);
            mVideoView.seekTo(mCurrentPosition);

            JSONObject jsonObject = new JSONObject("{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showProductDetail(List<ProductDetailModel> model) {
        if (model == null) return;
        mModelList = model;
        if (model.size() == 1) mSelectView.setVisibility(View.GONE);

        ProductDetailModel productDetailModel = model.get(0);
        String movieList = model.get(0).getMovieList();
        try {
//            JSONArray jsonArray = new JSONArray(movieList);
//            JSONObject jsonObject = jsonArray.getJSONObject(0);
//            mCurrentUrl = jsonObject.getString("movieurl");
//            mVideoView.setVideoPath(mCurrentUrl);
            String re = "http(.*)BreakPoint=0";
            Pattern p = Pattern.compile(re);
            Matcher m = p.matcher(movieList);
            if (m.find()) {
                mCurrentUrl = m.group();
            }
//            mVideoView.setVideoPath(Constants.TEMP_PLAY_URL);
            mVideoView.setVideoPath(mCurrentUrl);
            mNameView.setText(productDetailModel.getSeriesName());
            mTypeView.setText(productDetailModel.getProgramType2());
            mActorView.setText(productDetailModel.getWriterDisplay());
            mDirectorView.setText(productDetailModel.getActorDisplay());
            mYearView.setText(productDetailModel.getOrgairDate());
            mDescriptionView.setText(productDetailModel.getDescription());

            mSelectContainer.removeAllViews();
            for (int i = 0; i < model.size(); i++) {
                TextView textView = new TextView(this);
                ProductDetailModel productDetailModel1 = model.get(i);
//                JSONArray array = new JSONArray(productDetailModel1.getMovieList());
//                JSONObject jsonObject1 = array.getJSONObject(0);
//                String movieurl = jsonObject1.getString("movieurl");
//                textView.setTag(R.id.tag_first, movieurl);
                textView.setTag(R.id.tag_first, Constants.TEMP_PLAY_URL);
                textView.setTag(R.id.tag_second, i);
                textView.setText(i + "");
                textView.setBackgroundResource(R.drawable.select_item);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(12);
                textView.setTextColor(getResources().getColor(R.color.live_left));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(UIUtil.convertDpToPixel(this, 60), UIUtil.convertDpToPixel(this, 40));
                layoutParams.leftMargin = UIUtil.convertDpToPixel(this, 10);
                textView.setLayoutParams(layoutParams);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSelectContainer.getChildAt(mCurrentSelectPosition).setSelected(false);
                        mCurrentUrl = (String) view.getTag(R.id.tag_first);
                        mCurrentSelectPosition = (int) view.getTag(R.id.tag_second);
                        mSelectContainer.getChildAt(mCurrentSelectPosition).setSelected(true);
                        mProgress.setVisibility(View.VISIBLE);
//                    mVideoView.setVideoPath(mCurrentUrl);
                        mVideoView.setVideoPath(Constants.TEMP_PLAY_URL);
                        mControl.show();
                    }
                });
                textView.setSelected(i==0);
                mSelectContainer.addView(textView);
            }
            JSONObject jsonObject444 = new JSONObject("{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showRelativeProductInfo(List<ProductModel> model) {
        if (model != null) {
            mAdapter.setDatas(model);
        }
    }
}

package com.pbtd.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.adapter.TempTabAdapter;
import com.pbtd.mobile.model.ProductDetailModel;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.presenter.play.PlayContract;
import com.pbtd.mobile.presenter.play.PlayPresenter;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.widget.FixGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PlayActivity extends BaseActivity implements PlayContract.View {

    public static final String PRODUCT_CODE = "PRODUCT_CODE";
    private String mProduct_code = "";

    private PlayContract.Presenter mPresenter;
    private VideoView mVideoView;
    private RelativeLayout mSelectView;
    private TextView mNameView;
    private TextView mTypeView;
    private FixGridView mRelativeView;
    private TempTabAdapter mAdapter;
    private ScrollView mScrollView;
    private static int mCurrentPosition;
    private MediaController mMediaController;
    private String mCurrentUrl;
    private TextView mDirectorView;
    private TextView mActorView;
    private TextView mYearView;
    private TextView mDescriptionView;
    private RelativeLayout mDetailView;
    private TextView mSelectTitleView;
    private LinearLayout mSelectContainer;
    private TextView mUpdateView;
    private int mCurrentSelectPosition;

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
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        ImageView backView = (ImageView) findViewById(R.id.iv_back);
        ImageView fullScreenView = (ImageView) findViewById(R.id.iv_full_screen);
        ImageView mDesciptionButtonView = (ImageView) findViewById(R.id.iv_more);
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
        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
        ViewGroup.LayoutParams layoutParams = mMediaController.getLayoutParams();
        layoutParams.height = UIUtil.convertDpToPixel(this, 42);

        mVideoView.setOnPreparedListener((mp) -> {
            mp.start();
            mVideoView.seekTo(mCurrentPosition);
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

        backView.setOnClickListener((v -> this.finish()));

        mDesciptionButtonView.setOnClickListener((description_view) -> {
            mDetailView.setVisibility(mDetailView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mDesciptionButtonView.setSelected(mDetailView.getVisibility() == View.VISIBLE);
        });

        fullScreenView.setOnClickListener((full_view) -> {

            mCurrentPosition = mVideoView.getCurrentPosition();
            Intent intent = new Intent(this, PlayLandActivity.class);
            intent.putExtra(PlayLandActivity.URL, mCurrentUrl);
            intent.putExtra(PlayLandActivity.POSITION, mCurrentPosition);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCurrentPosition = getIntent().getIntExtra(PlayLandActivity.POSITION, 0);
        mVideoView.seekTo(mCurrentPosition);
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
        if (model.size() == 1) mSelectView.setVisibility(View.GONE);

        ProductDetailModel productDetailModel = model.get(0);
        String movieList = model.get(0).getMovieList();
        try {
            JSONArray jsonArray = new JSONArray(movieList);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            mCurrentUrl = jsonObject.getString("movieurl");
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
                JSONArray array = new JSONArray(productDetailModel1.getMovieList());
                JSONObject jsonObject1 = array.getJSONObject(0);
                String movieurl = jsonObject1.getString("movieurl");
                textView.setTag(R.id.tag_first, movieurl);
                textView.setTag(R.id.tag_second, i);
                textView.setText(i + "");
                textView.setBackgroundResource(R.drawable.select_item);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(12);
                textView.setTextColor(getResources().getColor(R.color.live_left));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(UIUtil.convertDpToPixel(this, 60), UIUtil.convertDpToPixel(this, 40));
                layoutParams.leftMargin = UIUtil.convertDpToPixel(this, 10);
                textView.setLayoutParams(layoutParams);
                textView.setOnClickListener((view) -> {
                    mSelectContainer.getChildAt(mCurrentSelectPosition).setSelected(false);
                    mCurrentUrl = (String) view.getTag(R.id.tag_first);
                    mCurrentSelectPosition = (int) view.getTag(R.id.tag_second);
                    mSelectContainer.getChildAt(mCurrentSelectPosition).setSelected(true);
                    mVideoView.setVideoPath(mCurrentUrl);

                });
                textView.setSelected(i==0);
                mSelectContainer.addView(textView);
            }

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

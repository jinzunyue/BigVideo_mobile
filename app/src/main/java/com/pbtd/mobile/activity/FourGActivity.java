package com.pbtd.mobile.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pbtd.mobile.R;
import com.pbtd.mobile.utils.UIUtil;

public class FourGActivity extends BaseActivity {

    public static final String IS_PLUS = "is_plus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_g);

        initView();
    }

    @Override
    protected void initView() {
        Button mProvinceView = (Button) findViewById(R.id.btn_province);
        Button mCountryView = (Button) findViewById(R.id.btn_country);
        Button mBuyView = (Button) findViewById(R.id.btn_buy);
        ImageView mBMobileView = (ImageView) findViewById(R.id.btn_mobile);
        ImageView mBAliView = (ImageView) findViewById(R.id.btn_ali);
        ImageView mBWechatView = (ImageView) findViewById(R.id.btn_wechat);
        ImageView mBackView = (ImageView) findViewById(R.id.iv_back);

        RelativeLayout mMobileView = (RelativeLayout) findViewById(R.id.rl_mobile);
        RelativeLayout mWechatView = (RelativeLayout) findViewById(R.id.rl_wechat);
        RelativeLayout mAliView = (RelativeLayout) findViewById(R.id.rl_ali);

        mBMobileView.setSelected(true);

        mProvinceView.setOnClickListener((province_view) -> UIUtil.showToast(this, "省内流量"));
        mCountryView.setOnClickListener((province_view) -> UIUtil.showToast(this, "全国流量"));
        mBuyView.setOnClickListener((province_view) -> UIUtil.showToast(this, "购买成功"));

        mMobileView.setOnClickListener((province_view) -> {
            mBMobileView.setSelected(true);
            mBAliView.setSelected(false);
            mBWechatView.setSelected(false);
        });
        mWechatView.setOnClickListener((province_view) -> {
            mBMobileView.setSelected(false);
            mBAliView.setSelected(false);
            mBWechatView.setSelected(true);
        });
        mAliView.setOnClickListener((province_view) -> {
            mBMobileView.setSelected(false);
            mBAliView.setSelected(true);
            mBWechatView.setSelected(false);
        });
        mBackView.setOnClickListener((back_view) -> this.finish());
    }
}

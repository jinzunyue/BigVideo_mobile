package com.pbtd.mobile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pbtd.mobile.R;
import com.pbtd.mobile.utils.UIUtil;

public class FourGActivity extends BaseActivity {

    public static final String IS_PLUS = "is_plus";
    private ImageView mBMobileView;
    private ImageView mBAliView;
    private ImageView mBWechatView;
    private ImageView mBackView;

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
        mBMobileView = (ImageView) findViewById(R.id.btn_mobile);
        mBAliView = (ImageView) findViewById(R.id.btn_ali);
        mBWechatView = (ImageView) findViewById(R.id.btn_wechat);
        mBackView = (ImageView) findViewById(R.id.iv_back);

        RelativeLayout mMobileView = (RelativeLayout) findViewById(R.id.rl_mobile);
        RelativeLayout mWechatView = (RelativeLayout) findViewById(R.id.rl_wechat);
        RelativeLayout mAliView = (RelativeLayout) findViewById(R.id.rl_ali);

        mBMobileView.setSelected(true);

        mProvinceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(FourGActivity.this, "省内流量");
            }
        });
        mCountryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(FourGActivity.this, "全国流量");
            }
        });
        mBuyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(FourGActivity.this, "购买成功");
            }
        });

        mMobileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBMobileView.setSelected(true);
                mBAliView.setSelected(false);
                mBWechatView.setSelected(false);
            }
        });
        mWechatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBMobileView.setSelected(false);
                mBAliView.setSelected(false);
                mBWechatView.setSelected(true);
            }
        });
        mAliView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBMobileView.setSelected(false);
                mBAliView.setSelected(true);
                mBWechatView.setSelected(false);
            }
        });
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FourGActivity.this.finish();
            }
        });
    }
}

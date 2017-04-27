package com.pbtd.mobile.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.utils.UIUtil;

public class VipBuyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_buy);

        Button btn_buy = (Button) findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener((view) -> UIUtil.showToast(this, "订购成功"));

        ImageView backView = (ImageView) findViewById(R.id.iv_back);
        backView.setOnClickListener((back_view) -> this.finish());
    }
}

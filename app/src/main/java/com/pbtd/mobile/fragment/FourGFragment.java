package com.pbtd.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.FourGActivity;
import com.pbtd.mobile.activity.VipBuyActivity;
import com.pbtd.mobile.adapter.FourGPagerAdapter;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.widget.PagerSlidingTabStrip;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class FourGFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4g, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        LinearLayout mVipView = (LinearLayout) view.findViewById(R.id.ll_vip);
        LinearLayout mQuickView = (LinearLayout) view.findViewById(R.id.quick_4g);
        LinearLayout mPlusView = (LinearLayout) view.findViewById(R.id.plus_4g);
        LinearLayout mMoreView = (LinearLayout) view.findViewById(R.id.more);

        mVipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, VipBuyActivity.class));
            }
        });
        mQuickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, FourGActivity.class);
                intent.putExtra(FourGActivity.IS_PLUS, false);
                startActivity(intent);
            }
        });
        mPlusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, FourGActivity.class);
                intent.putExtra(FourGActivity.IS_PLUS, true);
                startActivity(intent);
            }
        });
        mMoreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(mActivity, "更多业务");
            }
        });

        PagerSlidingTabStrip mIndicator = (PagerSlidingTabStrip) view.findViewById(R.id.indicator);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        FourGPagerAdapter mAdapter = new FourGPagerAdapter(mActivity);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager);

    }
}

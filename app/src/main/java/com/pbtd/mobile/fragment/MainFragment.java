package com.pbtd.mobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pbtd.mobile.R;
import com.pbtd.mobile.UIUtil;
import com.pbtd.mobile.adapter.MainTabAdapter;
import com.pbtd.mobile.widget.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class MainFragment extends BaseFragment {

    private ArrayList<BaseFragment> mTestTabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RelativeLayout searchView = (RelativeLayout) view.findViewById(R.id.rl_search);
        ImageView historyView = (ImageView) view.findViewById(R.id.iv_history);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        PagerSlidingTabStrip indicator = (PagerSlidingTabStrip) view.findViewById(R.id.indicator);
        ImageView moreTabView = (ImageView) view.findViewById(R.id.iv_more);

        MainTabAdapter mAdapter = new MainTabAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);


        mTestTabs = new ArrayList<>();

        TabItemFragment recomment = new TabItemFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(TabItemFragment.IS_RECOMMENT, true);
        recomment.setArguments(bundle);
        recomment.setTitle("推荐");
        mTestTabs.add(recomment);

        TabItemFragment tvFragment = new TabItemFragment();
        Bundle bundle_tv = new Bundle();
        bundle_tv.putBoolean(TabItemFragment.IS_RECOMMENT, false);
        tvFragment.setTitle("电视剧");
        tvFragment.setArguments(bundle_tv);
        mTestTabs.add(tvFragment);

        TabItemFragment movieFragment = new TabItemFragment();
        Bundle bundle_movie = new Bundle();
        bundle_movie.putBoolean(TabItemFragment.IS_RECOMMENT, false);
        movieFragment.setTitle("电影");
        movieFragment.setArguments(bundle_movie);
        mTestTabs.add(movieFragment);

        TabItemFragment varietyFragment = new TabItemFragment();
        Bundle bundle_variety = new Bundle();
        bundle_variety.putBoolean(TabItemFragment.IS_RECOMMENT, false);
        varietyFragment.setTitle("综艺");
        varietyFragment.setArguments(bundle_variety);
        mTestTabs.add(varietyFragment);

        TabItemFragment entertainmentFragment = new TabItemFragment();
        Bundle bundle_entertainment = new Bundle();
        bundle_entertainment.putBoolean(TabItemFragment.IS_RECOMMENT, false);
        entertainmentFragment.setTitle("娱乐");
        entertainmentFragment.setArguments(bundle_entertainment);
        mTestTabs.add(entertainmentFragment);

        mAdapter.setData(mTestTabs);
        indicator.setViewPager(viewPager);


        searchView.setOnClickListener((view_search) -> UIUtil.showToast(getActivity(), "搜索"));
        historyView.setOnClickListener((view_search) -> UIUtil.showToast(getActivity(), "播放记录"));
        moreTabView.setOnClickListener((view_search) -> UIUtil.showToast(getActivity(), "更多频道"));

    }
}

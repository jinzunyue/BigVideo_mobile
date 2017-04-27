package com.pbtd.mobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pbtd.mobile.Constants;
import com.pbtd.mobile.R;
import com.pbtd.mobile.model.NavigationInfoModel;
import com.pbtd.mobile.presenter.main.MainContract;
import com.pbtd.mobile.presenter.main.MainPresenter;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.adapter.MainTabAdapter;
import com.pbtd.mobile.widget.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class MainFragment extends BaseFragment implements MainContract.View{

    private MainPresenter mPresenter;
    private MainTabAdapter mAdapter;
    private PagerSlidingTabStrip mIndicator;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new MainPresenter(mActivity, this);
//        mPresenter.getNavigationInfo();
        showNavigation(null);
    }

    private void initView(View view) {
        RelativeLayout searchView = (RelativeLayout) view.findViewById(R.id.rl_search);
        ImageView historyView = (ImageView) view.findViewById(R.id.iv_history);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mIndicator = (PagerSlidingTabStrip) view.findViewById(R.id.indicator);
        ImageView moreTabView = (ImageView) view.findViewById(R.id.iv_more);

        mAdapter = new MainTabAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);

        searchView.setOnClickListener((view_search) -> UIUtil.showToast(getActivity(), "搜索"));
        historyView.setOnClickListener((view_search) -> UIUtil.showToast(getActivity(), "播放记录"));
        moreTabView.setOnClickListener((view_search) -> UIUtil.showToast(getActivity(), "更多频道"));

    }

    @Override
    public void showError(String error) {
        UIUtil.showToast(mActivity, error+"");
    }

    @Override
    public void showNavigation(NavigationInfoModel navigationInfoModel) {
        ArrayList<BaseFragment> tabs = new ArrayList<>();
//        for (int i = 0; i < navigationInfoModel.navigationItem.size(); i++) {
//            NavigationInfoModel.NavigationItem navigationItem = navigationInfoModel.navigationItem.get(i);
//            TabItemFragment recomment = new TabItemFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString(TabItemFragment.RECOMMEND_CODE, navigationItem.recommendCode);
//            recomment.setArguments(bundle);
//            recomment.setTitle(navigationItem.name);
//            tabs.add(recomment);
//        }

        TempFragment tempFragment = new TempFragment();
        tempFragment.setTitle("电影");
        Bundle bundle = new Bundle();
        bundle.putString(TempFragment.PACKAGE_CODE, Constants.TEMP_MOVIE_PACKAGE_CODE);
        tempFragment.setArguments(bundle);
        tabs.add(tempFragment);

        TempFragment tempFragment_tv = new TempFragment();
        tempFragment_tv.setTitle("电视剧");
        Bundle bundle_tv = new Bundle();
        bundle_tv.putString(TempFragment.PACKAGE_CODE, Constants.TEMP_TV_PACKAGE_CODE);
        tempFragment_tv.setArguments(bundle_tv);
        tabs.add(tempFragment_tv);

        TempFragment tempFragment_variety = new TempFragment();
        tempFragment_variety.setTitle("综艺");
        Bundle bundle_variety = new Bundle();
        bundle_variety.putString(TempFragment.PACKAGE_CODE, Constants.TEMP_VARIETY_PACKAGE_CODE);
        tempFragment_variety.setArguments(bundle_variety);
        tabs.add(tempFragment_variety);

        TempFragment tempFragment_anime = new TempFragment();
        tempFragment_anime.setTitle("动漫");
        Bundle bundle_anime = new Bundle();
        bundle_anime.putString(TempFragment.PACKAGE_CODE, Constants.TEMP_ANIME_PACKAGE_CODE);
        tempFragment_anime.setArguments(bundle_anime);
        tabs.add(tempFragment_anime);

        TempFragment tempFragment_reco = new TempFragment();
        tempFragment_reco.setTitle("纪录片");
        Bundle bundle_reco = new Bundle();
        bundle_reco.putString(TempFragment.PACKAGE_CODE, Constants.TEMP_RECOMDER_PACKAGE_CODE);
        tempFragment_reco.setArguments(bundle_reco);
        tabs.add(tempFragment_reco);

        mAdapter.setData(tabs);
        mIndicator.setViewPager(mViewPager);
    }
}

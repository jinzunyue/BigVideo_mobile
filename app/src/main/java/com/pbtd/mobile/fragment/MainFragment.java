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
import com.pbtd.mobile.adapter.MainTabAdapter;
import com.pbtd.mobile.model.CategoryModel;
import com.pbtd.mobile.presenter.main.MainContract;
import com.pbtd.mobile.presenter.main.MainPresenter;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

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
        mPresenter.getCategoryList();
    }

    private void initView(View view) {
        RelativeLayout searchView = (RelativeLayout) view.findViewById(R.id.rl_search);
        ImageView historyView = (ImageView) view.findViewById(R.id.iv_history);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mIndicator = (PagerSlidingTabStrip) view.findViewById(R.id.indicator);
        ImageView moreTabView = (ImageView) view.findViewById(R.id.iv_more);

        mAdapter = new MainTabAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(getActivity(), "搜索");
            }
        });
        historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(getActivity(), "播放记录");
            }
        });
        moreTabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(getActivity(), "更多频道");
            }
        });
    }

    @Override
    public void showError(String error) {
        UIUtil.showToast(mActivity, error+"");
    }

    @Override
    public void showCategoryList(List<CategoryModel> list) {
        List<BaseFragment> tabs = new ArrayList<>();
        ArrayList<String> recommendModelList = new ArrayList<>();
        recommendModelList.add("推荐");

        RecommendFragment recommendFragment = new RecommendFragment();
        tabs.add(recommendFragment);

        for (CategoryModel model : list) {
            String name = model.getName();
            String categorycode = model.getCategorycode();
            TabItemFragment itemFragment = new TabItemFragment();
            itemFragment.setTitle(name);
            Bundle bundle = new Bundle();
            bundle.putString(TabItemFragment.CATEGORY_CODE, categorycode);
            itemFragment.setArguments(bundle);
            tabs.add(itemFragment);

            recommendModelList.add(name);
        }
        recommendFragment.setTitle("推荐");
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(RecommendFragment.LIST, recommendModelList);
        recommendFragment.setArguments(bundle);

        mAdapter.setData(tabs);
        mIndicator.setViewPager(mViewPager);
    }
}

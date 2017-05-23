package com.pbtd.mobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.adapter.PageItemAdapter;
import com.pbtd.mobile.adapter.RecommentPagerAdapter;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.model.temp.RecommendModel;
import com.pbtd.mobile.presenter.tab.TabContract;
import com.pbtd.mobile.presenter.tab.TabPresenter;
import com.pbtd.mobile.utils.UIUtil;
import com.pbtd.mobile.widget.refresh.PullToRefreshLayout;
import com.pbtd.mobile.widget.refresh.PullableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class RecommendFragment extends BaseFragment implements TabContract.View {

    public static final String LIST = "list";
    private RecommentPagerAdapter mViewPagerAdapter;
    private LinearLayout mIndicator;
    private TextView mTitleView;
    private PullableListView mContentView;
    private PageItemAdapter mPageItemAdapter;
    private ViewPager mViewPager;
    private TabContract.Presenter mPresenter;
    private List<RecommendModel> mRecommendModelList;
    private List<String> mRecommendTitleList;
    public static HashMap<String, Integer> mRecommendIcon = new HashMap<>();
    private PullToRefreshLayout mRefresh;
    public static final long AUTO_SWITCH = 3000;

//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 0) {
//                int currentItem = mViewPager.getCurrentItem();
//                mViewPager.setCurrentItem(currentItem + 1);
//                mHandler.sendEmptyMessageDelayed(0, AUTO_SWITCH);
//            }
//        }
//    };
    private boolean mIsRefreshIng;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mRecommendTitleList = arguments.getStringArrayList(LIST);

        mRecommendIcon.put("电视剧", R.mipmap.tv_video);
        mRecommendIcon.put("电影", R.mipmap.movie);
        mRecommendIcon.put("综艺", R.mipmap.variety);
        mRecommendIcon.put("动漫", R.mipmap.anime);
        mRecommendIcon.put("纪录片", R.mipmap.information);// TODO: 17/5/4 这里用的是资讯的图片
        mRecommendIcon.put("推荐", R.mipmap.hot);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recomment, null);
        View header_view = inflater.inflate(R.layout.header_page_item, null);
        View footer_view = inflater.inflate(R.layout.footer_view, null);

        initView(view, header_view, footer_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new TabPresenter(mActivity, this);
        mPresenter.getProductList(null, null, null);
    }

    private void initView(View view, View header_view, View footer_view) {

        LinearLayout left_4g = (LinearLayout) header_view.findViewById(R.id.left_4g);
        LinearLayout recomment = (LinearLayout) header_view.findViewById(R.id.recomment);
        LinearLayout sign_in = (LinearLayout) header_view.findViewById(R.id.sign_in);
        LinearLayout subject = (LinearLayout) header_view.findViewById(R.id.subject);
        mViewPager = (ViewPager) header_view.findViewById(R.id.view_pager);
        mIndicator = (LinearLayout) header_view.findViewById(R.id.ll_indicator);
        mTitleView = (TextView) header_view.findViewById(R.id.tv_title);
        mRefresh = (PullToRefreshLayout) view.findViewById(R.id.refresh);
        mRefresh.setEnableLoadMore(false);
        mRefresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                mIsRefreshIng = true;
                mPresenter.getProductList(null, null, null);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });

        Button adjustView = (Button) footer_view.findViewById(R.id.btn_adjust);
        mContentView = (PullableListView) view.findViewById(R.id.lv);

        mViewPagerAdapter = new RecommentPagerAdapter(getActivity());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = mViewPagerAdapter.getRealPosition(position);
                switchIndicator(realPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPageItemAdapter = new PageItemAdapter(getActivity(), true);
        mContentView.addHeaderView(header_view);
        mContentView.addFooterView(footer_view);
        mContentView.setAdapter(mPageItemAdapter);

        left_4g.setOnClickListener((view_left_4g) -> UIUtil.showToast(getActivity(), "剩余流量"));
        recomment.setOnClickListener((view_left_4g) -> UIUtil.showToast(getActivity(), "推荐有礼"));
        sign_in.setOnClickListener((view_left_4g) -> UIUtil.showToast(getActivity(), "每日签到"));
        subject.setOnClickListener((view_left_4g) -> UIUtil.showToast(getActivity(), "精选专题"));

        adjustView.setOnClickListener((adjust_view) -> UIUtil.showToast(getActivity(), "调整栏目"));

    }


    private void initIndicator(int size) {
        mIndicator.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(UIUtil.convertDpToPixel(getActivity(), 4), UIUtil.convertDpToPixel(getActivity(), 4));
        layoutParams.leftMargin = UIUtil.convertDpToPixel(getActivity(), 6);
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackground(getActivity().getResources().getDrawable(R.drawable.selected_indicator));
            imageView.setEnabled(i == 0);
            if (i != 0) imageView.setLayoutParams(layoutParams);
            mIndicator.addView(imageView);
        }
    }

    private void switchIndicator(int position) {
        for (int i = 0; i < mIndicator.getChildCount(); i++) {
            mIndicator.getChildAt(i).setEnabled(i == position);
        }
    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public void showError(String error) {
        UIUtil.showToast(mActivity, error);
    }

    @Override
    public void showProductList(List<ProductModel> list) {
        if (mIsRefreshIng) {
            mIsRefreshIng = false;
            mRefresh.refreshFinish(PullToRefreshLayout.SUCCEED);
        }

        mRecommendModelList = new ArrayList<>();
        for (String name : mRecommendTitleList) {
            RecommendModel r = new RecommendModel();
            r.setName(name);
            for (ProductModel model : list) {
                if (model.getProgramtype().equals(name))
                    r.getList().add(model);
            }
            mRecommendModelList.add(r);
        }

        mPageItemAdapter.setData(mRecommendModelList);

        for (int i = 0; i < mRecommendModelList.size(); i++) {
            RecommendModel recommendModel = mRecommendModelList.get(i);
            if (recommendModel.getName().equals("推荐")) {
                List<ProductModel> list1 = recommendModel.getList();
                List<ProductModel> list2 = new ArrayList<>();
                list2.addAll(list1);
                if (list2.size() > 4) {
                    mViewPagerAdapter.setData(list2.subList(4, list1.size() - 1));
                } else {
                    mViewPagerAdapter.setData(list2);
                }
                mViewPager.setCurrentItem(5000);
                initIndicator(mViewPagerAdapter.getDataSize());

            }
        }
    }
}

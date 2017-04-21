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
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.UIUtil;
import com.pbtd.mobile.adapter.PageItemAdapter;
import com.pbtd.mobile.adapter.RecommentPagerAdapter;
import com.pbtd.mobile.model.RecommendModel;
import com.pbtd.mobile.presenter.recomment.RecommentContract;
import com.pbtd.mobile.presenter.recomment.RecommentPresenter;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class TabItemFragment extends BaseFragment implements RecommentContract.View{

    public static final String IS_RECOMMENT = "isRecomment";
    private boolean mIsRecommnetPage;

    RecommentContract.Presenter mPresenter;
    private RecommentPagerAdapter mViewPagerAdapter;
    private LinearLayout mIndicator;
    private TextView mTitleView;
    private ListView mContentView;
    private PageItemAdapter mPageItemAdapter;
    private ViewPager mViewPager;
    private SimpleDraweeView mTopView_header2;
    private TextView mTitle_header2;
    private LinearLayout mBottom_header2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments!=null) {
            mIsRecommnetPage = arguments.getBoolean(IS_RECOMMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO: 17/4/21 重构
        View view = inflater.inflate(R.layout.fragment_recomment, null);
        View header_view = inflater.inflate(R.layout.header_page_item, null);
        View footer_view = inflater.inflate(R.layout.footer_view, null);
        View header_view2 = inflater.inflate(R.layout.header_view2, null);

        initView(view, header_view, footer_view, header_view2);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new RecommentPresenter(getContext(), this);
        mPresenter.getRecomment(0);
    }

    private void initView(View view, View header_view, View footer_view, View header_view2) {
        LinearLayout left_4g = (LinearLayout) header_view.findViewById(R.id.left_4g);
        LinearLayout recomment = (LinearLayout) header_view.findViewById(R.id.recomment);
        LinearLayout sign_in = (LinearLayout) header_view.findViewById(R.id.sign_in);
        LinearLayout subject = (LinearLayout) header_view.findViewById(R.id.subject);
        mViewPager = (ViewPager) header_view.findViewById(R.id.view_pager);
        mIndicator = (LinearLayout) header_view.findViewById(R.id.ll_indicator);
        mTitleView = (TextView) header_view.findViewById(R.id.tv_title);

        mTopView_header2 = (SimpleDraweeView) header_view2.findViewById(R.id.sd_view);
        mTitle_header2 = (TextView) header_view2.findViewById(R.id.tv_tip);
        mBottom_header2 = (LinearLayout) header_view2.findViewById(R.id.ll_bottom);

        Button adjustView = (Button) footer_view.findViewById(R.id.btn_adjust);

        mContentView = (ListView) view.findViewById(R.id.lv);

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
        mContentView.addHeaderView(mIsRecommnetPage?header_view:header_view2);
        if (mTitle.equals("娱乐")) mBottom_header2.setVisibility(View.GONE);
        if (mIsRecommnetPage)mContentView.addFooterView(footer_view);
        mContentView.setAdapter(mPageItemAdapter);

        left_4g.setOnClickListener((view_left_4g) -> UIUtil.showToast(getActivity(), "剩余流量"));
        recomment.setOnClickListener((view_left_4g) -> UIUtil.showToast(getActivity(), "推荐有礼"));
        sign_in.setOnClickListener((view_left_4g) -> UIUtil.showToast(getActivity(), "每日签到"));
        subject.setOnClickListener((view_left_4g) -> UIUtil.showToast(getActivity(), "精选专题"));

        adjustView.setOnClickListener((adjust_view) -> UIUtil.showToast(getActivity(), "调整栏目"));
    }

    @Override
    public void setRecomment(List<RecommendModel> list) {

        if (mIsRecommnetPage) {
            mViewPagerAdapter.setData(list.get(0).getRecommendedVideos());
//        mViewPager.setCurrentItem(Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%mViewPagerAdapter.getDataSize());

            int size = 0;
            if (list!=null && list.get(0)!=null && list.get(0).getRecommendedVideos()!=null)
                size = list.get(0).getRecommendedVideos().size();
            initIndicator(size);
        } else {
            mTopView_header2.setImageURI(list.get(0).getRecommendedVideos().get(0).getImageURL());
            mTitle_header2.setText(list.get(0).getRecommendedVideos().get(0).getTitle());
        }

        mPageItemAdapter.setData(list);
    }

    private void initIndicator(int size) {
        mIndicator.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(UIUtil.convertDpToPixel(getActivity(), 4), UIUtil.convertDpToPixel(getActivity(), 4));
        layoutParams.leftMargin = UIUtil.convertDpToPixel(getActivity(), 6);
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackground(getActivity().getResources().getDrawable(R.drawable.selected_indicator));
            imageView.setEnabled(i==0);
            if (i!=0) imageView.setLayoutParams(layoutParams);
            mIndicator.addView(imageView);
        }
    }

    private void switchIndicator(int position) {
        for (int i = 0; i < mIndicator.getChildCount(); i++) {
            mIndicator.getChildAt(i).setEnabled(i==position);
        }
    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
    }
}

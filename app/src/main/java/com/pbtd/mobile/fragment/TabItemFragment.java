package com.pbtd.mobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.adapter.PageItemAdapter;
import com.pbtd.mobile.adapter.RecommentPagerAdapter;
import com.pbtd.mobile.model.ProductInfoModel;
import com.pbtd.mobile.model.RecommendModel;
import com.pbtd.mobile.model.RecommendVideoModel;
import com.pbtd.mobile.presenter.recomment.RecommentContract;
import com.pbtd.mobile.presenter.recomment.RecommentPresenter;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class TabItemFragment extends BaseFragment implements RecommentContract.View {

    public static final String RECOMMEND_CODE = "recommendCode";
    public static final String PACKAGE_CODE = "packageCode";

    private RecommentPagerAdapter mViewPagerAdapter;
    private ListView mContentView;
    private PageItemAdapter mPageItemAdapter;
    private SimpleDraweeView mTopView_header2;
    private TextView mTitle_header2;
    private LinearLayout mBottom_header2;
    private RecommentPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String recommend_code = arguments.getString(RECOMMEND_CODE);
            String package_code = arguments.getString(PACKAGE_CODE);
            mPresenter = new RecommentPresenter(mActivity, this);
//            mPresenter.getRecommentVideo(recommend_code);
            mPresenter.getProductInfoList(package_code);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO: 17/4/21 重构
        View view = inflater.inflate(R.layout.fragment_recomment, null);
        View header_view2 = inflater.inflate(R.layout.header_view2, null);

        initView(view,header_view2);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view, View header_view2) {

        mTopView_header2 = (SimpleDraweeView) header_view2.findViewById(R.id.sd_view);
        mTitle_header2 = (TextView) header_view2.findViewById(R.id.tv_tip);
        mBottom_header2 = (LinearLayout) header_view2.findViewById(R.id.ll_bottom);

        mContentView = (ListView) view.findViewById(R.id.lv);

        mPageItemAdapter = new PageItemAdapter(getActivity(), true);
        mContentView.addHeaderView(header_view2);
        if (mTitle.equals("4K")) mBottom_header2.setVisibility(View.GONE);
        mContentView.setAdapter(mPageItemAdapter);

    }

    @Override
    public void setRecomment(List<RecommendModel> list) {
        mTopView_header2.setImageURI(list.get(0).getRecommendedVideos().get(0).getImageURL());
        mTitle_header2.setText(list.get(0).getRecommendedVideos().get(0).getTitle());
        mPageItemAdapter.setData(list);
    }

    @Override
    public void showRecommendVideo(RecommendVideoModel model) {

    }

    @Override
    public void showProductInfoList(List<ProductInfoModel> list) {

    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
    }
}

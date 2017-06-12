package com.pbtd.mobile.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.canyinghao.canrefresh.CanRefreshLayout;
import com.orhanobut.logger.Logger;
import com.pbtd.mobile.R;
import com.pbtd.mobile.adapter.PageItemAdapter;
import com.pbtd.mobile.adapter.RecommentPagerAdapter;
import com.pbtd.mobile.model.ProductModel;
import com.pbtd.mobile.model.temp.RecommendModel;
import com.pbtd.mobile.presenter.tab.TabContract;
import com.pbtd.mobile.presenter.tab.TabPresenter;
import com.pbtd.mobile.utils.UIUtil;

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
    private ListView mContentView;
    private PageItemAdapter mPageItemAdapter;
    private ViewPager mViewPager;
    private TabContract.Presenter mPresenter;
    private List<RecommendModel> mRecommendModelList;
    private List<String> mRecommendTitleList;
    public static HashMap<String, Integer> mRecommendIcon = new HashMap<>();
    private CanRefreshLayout mRefresh;
    public static final long AUTO_SWITCH = 3000;
    public static final int MSG_AUTO_SWITH = 0;
    public static final int MSG_REFRESH_PAGE_ITEM = 1;
    public static final int MSG_REFRESH_HEADER = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUTO_SWITH:
                    Logger.d("msg  轮播收到");
                    int currentItem = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(currentItem + 1);
                    mHandler.sendEmptyMessageDelayed(MSG_AUTO_SWITH, AUTO_SWITCH);
                    Logger.d("msg  轮播开始");
                    break;
                case MSG_REFRESH_PAGE_ITEM:
                    Logger.d("MSG_REFRESH_PAGE_ITEM");
                    mPageItemAdapter.setDatas(mRecommendModelList);
                    break;
                case MSG_REFRESH_HEADER:
                    Logger.d("MSG_REFRESH_HEADER");
                    List<ProductModel> obj = (List<ProductModel>) msg.obj;
                    Logger.d("完成  强转");
                    mViewPagerAdapter.setData(obj);
                    Logger.d("完成  setData");
                    mViewPager.setCurrentItem(500);
                    Logger.d("完成  currentItem");

                    initIndicator(mViewPagerAdapter.getDataSize());
                    if (mIsRefreshIng) {
                        mIsRefreshIng = false;
                        mRefresh.refreshComplete();
                    }
                    mHandler.sendEmptyMessageDelayed(MSG_AUTO_SWITH, AUTO_SWITCH);

                    break;
            }

        }
    };
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

        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new TabPresenter(mActivity, this);
        mPresenter.getProductList(null, null, null);
    }

    private void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mIndicator = (LinearLayout) view.findViewById(R.id.ll_indicator);
        mTitleView = (TextView) view.findViewById(R.id.tv_title);

        mRefresh = (CanRefreshLayout) view.findViewById(R.id.refresh);
        mRefresh.setOnRefreshListener(new CanRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPresenter != null) {
                    mPresenter.getProductList(null, null, null);
                    mIsRefreshIng = true;
                    mHandler.removeMessages(MSG_AUTO_SWITH);
                }
            }
        });
        Button adjustView = (Button) view.findViewById(R.id.btn_adjust);
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

        mPageItemAdapter = new PageItemAdapter(getActivity());
        mContentView.setAdapter(mPageItemAdapter);

        adjustView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showToast(getActivity(), "调整栏目");
            }
        });

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("Refresh : down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("Refresh : move");
                        mHandler.removeMessages(MSG_AUTO_SWITH);
                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("Refresh : up");
                        mHandler.sendEmptyMessageDelayed(MSG_AUTO_SWITH, AUTO_SWITCH);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        System.out.println("Refresh : cancel");
                        break;
                }
                return false;
            }
        });
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.d("徐勤超" + hidden);
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
    public void showProductList(final List<ProductModel> list) {
        mRecommendModelList = new ArrayList<>();
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                for (String name : mRecommendTitleList) {
                    RecommendModel r = new RecommendModel();
                    r.setName(name);
                    for (ProductModel model : list) {
                        if (model.getProgramtype().equals(name))
                            r.getList().add(model);
                    }
                    if (!r.getName().equals("特色") && !r.getName().equals("精品影视"))
                        mRecommendModelList.add(r);
                }

                mHandler.sendEmptyMessage(MSG_REFRESH_PAGE_ITEM);
            }
        });

        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mRecommendModelList.size(); i++) {
                    RecommendModel recommendModel = mRecommendModelList.get(i);
                    if (recommendModel.getName().equals("推荐")) {
                        List<ProductModel> list1 = recommendModel.getList();
                        List<ProductModel> list2 = new ArrayList<>();
                        list2.addAll(list1);
                        if (list2.size() > 4) {
                            list2 = list2.subList(4, list1.size() - 1);
                        }

                        Message message = mHandler.obtainMessage();
                        message.what = MSG_REFRESH_HEADER;
                        message.obj = list2;
                        mHandler.sendMessage(message);
                        break;
                    }
                }
            }
        }, 300);
    }
}

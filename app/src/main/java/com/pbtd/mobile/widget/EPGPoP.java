package com.pbtd.mobile.widget;

import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.pbtd.mobile.Constants;
import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.BaseActivity;
import com.pbtd.mobile.adapter.LiveProgramAdapter;
import com.pbtd.mobile.model.live.CategoryInnerModel;
import com.pbtd.mobile.model.live.WeekProgramModel;
import com.pbtd.mobile.presenter.live.LiveContract;
import com.pbtd.mobile.presenter.live.LivePresenter;
import com.pbtd.mobile.utils.StringUtil;
import com.pbtd.mobile.utils.UIUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuqinchao on 17/5/9.
 */

public class EPGPoP extends PopupWindow implements LiveContract.View, View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView mBackView;
    @BindView(R.id.ll_title)
    LinearLayout mTitleView;
    @BindView(R.id.lv)
    ListView mListView;

    private BaseActivity mContext;
    private Listener mListener;
    private CategoryInnerModel mModel;
    private LivePresenter mPresenter;
    private HashMap<String, List<WeekProgramModel>> mDataMap = new HashMap<>();
    private List<String> mTitleList;
    private LiveProgramAdapter mAdapter;
    private TextView mCurrentText;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                mTitleView.removeAllViews();
                for (int i = 0; i < mTitleList.size(); i++) {
                    TextView textView = new TextView(mContext);
                    textView.setText(mTitleList.get(i));
                    textView.setTextSize(13);
                    int left = UIUtil.convertDpToPixel(mContext, 8);
                    int top = UIUtil.convertDpToPixel(mContext, 3);
                    textView.setPadding(left, top, left, top);
                    textView.setTextColor(mContext.getResources().getColor(R.color.black));
                    textView.setOnClickListener(EPGPoP.this);
                    textView.setTag(mDataMap.get(mTitleList.get(i)));
                    mTitleView.addView(textView);
                    if (i == 1) {
                        mCurrentText = textView;
                        textView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    }
                }

                mAdapter.setDatas(mDataMap.get("今天"));
                Logger.d(mDataMap);
            }
        }
    };

    public EPGPoP(BaseActivity context) {
        mContext = context;
        View conentView = View.inflate(context, R.layout.item_epg_pop, null);
        ButterKnife.bind(this, conentView);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(h - UIUtil.convertDpToPixel(mContext, 285));
        // 设置SelectPicPopupindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        initView();
    }

    private void initView() {

        mTitleList = new ArrayList<>();
        mTitleList.add("明天");
        mTitleList.add("今天");
        mTitleList.add(StringUtil.convertDate(StringUtil.getCurrentDate(-1)));
        mTitleList.add(StringUtil.convertDate(StringUtil.getCurrentDate(-2)));
        mTitleList.add(StringUtil.convertDate(StringUtil.getCurrentDate(-3)));
        mTitleList.add(StringUtil.convertDate(StringUtil.getCurrentDate(-4)));
//        mTitleList.add(StringUtil.convertDate(StringUtil.getCurrentDate(-5)));

        for (int i = 0; i < mTitleList.size(); i++) {
            mDataMap.put(mTitleList.get(i), new ArrayList<WeekProgramModel>());
        }

        mAdapter = new LiveProgramAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EPGPoP.this.dismiss();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WeekProgramModel weekProgramModel = mAdapter.getDatas().get(position);
                long startTime = weekProgramModel.getStartTime() * 1000;
                if (startTime > System.currentTimeMillis()) {
                    // TODO: 17/6/12 发送预约
                    weekProgramModel.setSubscribe(weekProgramModel.getSubscribe() ^ 1);
                    mAdapter.notifyDataSetChanged();
                } else {
                    long endTime = weekProgramModel.getEndTime();
                    long l = endTime - startTime / 1000;
                    String liveBackUrl = StringUtil.getLiveBackUrl(Constants.CCTV_9, startTime, l);

                    if (mListener != null)
                        mListener.onBackUrl(liveBackUrl);
                }
            }
        });

        mPresenter = new LivePresenter(mContext, this);
    }

    public void setData(CategoryInnerModel model) {
        mModel = model;
        mPresenter.getProgramOfWeek(mModel.getVideoId() + "");
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showCategoryList(List<CategoryInnerModel> list) {

    }

    @Override
    public void showProgramWeek(final List<WeekProgramModel> list) {
        if (list == null) return;

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < list.size(); i++) {
                            WeekProgramModel weekProgramModel = list.get(i);
                            String startDate = weekProgramModel.getStartDate();

                            if (startDate.equals(StringUtil.getCurrentDate(1))) {
                                mDataMap.get("明天").add(weekProgramModel);
                            } else if (startDate.equals(StringUtil.getCurrentDate(0))) {
                                mDataMap.get("今天").add(weekProgramModel);
                            } else {
                                mDataMap.get(StringUtil.convertDate(startDate)).add(weekProgramModel);
                            }
                        }

                        Set<String> strings = mDataMap.keySet();
                        for (String key : strings) {
                            List<WeekProgramModel> weekProgramModels = mDataMap.get(key);
                            Collections.sort(weekProgramModels, new Comparator<WeekProgramModel>() {
                                @Override
                                public int compare(WeekProgramModel o1, WeekProgramModel o2) {
                                    return (int) (o1.getStartTime() - o2.getStartTime());
                                }
                            });
                        }

                        mHandler.sendEmptyMessage(100);
                    }
                }
        ).start();

    }

    @Override
    public void onClick(View v) {
        mCurrentText.setTextColor(mContext.getResources().getColor(R.color.black));
        TextView t = (TextView) v;
        t.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        mCurrentText = t;
        List<WeekProgramModel> tag = (List<WeekProgramModel>) v.getTag();
        mAdapter.setDatas(tag);
    }

    public interface Listener {
        void onBackUrl(String backUrl);
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    private void doAlphaAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.3f);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setBackgroundAlpha((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mContext.getWindow().setAttributes(lp);
    }
}

package com.pbtd.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.HistoryAndCollectionActivity;
import com.pbtd.mobile.utils.UIUtil;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class MyFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        SimpleDraweeView mUpAvatarView = (SimpleDraweeView) view.findViewById(R.id.sd_avatar);
        TextView mNoteView = (TextView) view.findViewById(R.id.tv_note);
        RelativeLayout mMessageView = (RelativeLayout) view.findViewById(R.id.rl_message);
        RelativeLayout mHistoryView = (RelativeLayout) view.findViewById(R.id.rl_history);
        RelativeLayout mCollectionView = (RelativeLayout) view.findViewById(R.id.rl_collection);
        RelativeLayout mOrderView = (RelativeLayout) view.findViewById(R.id.rl_order);
        RelativeLayout mBindView = (RelativeLayout) view.findViewById(R.id.rl_bind);
        RelativeLayout mVipView = (RelativeLayout) view.findViewById(R.id.rl_vip);
        RelativeLayout mSuggestionView = (RelativeLayout) view.findViewById(R.id.rl_suggestion);
        RelativeLayout mQuestionView = (RelativeLayout) view.findViewById(R.id.rl_question);
        RelativeLayout mAboutView = (RelativeLayout) view.findViewById(R.id.rl_about);
        RelativeLayout mSetView = (RelativeLayout) view.findViewById(R.id.rl_set);

        mUpAvatarView.setOnClickListener((up_view) -> UIUtil.showToast(mActivity, "上传头像"));
        mNoteView.setOnClickListener((up_view) -> UIUtil.showToast(mActivity, "签到成功"));
        mMessageView.setOnClickListener((up_view) -> UIUtil.showToast(mActivity, "我的消息"));
        mHistoryView.setOnClickListener((up_view) -> {
            Intent intent = new Intent(mActivity, HistoryAndCollectionActivity.class);
            intent.putExtra(HistoryAndCollectionActivity.IS_HISTORY, true);
            mActivity.startActivity(intent);
        });
        mCollectionView.setOnClickListener((up_view) -> {
            Intent intent = new Intent(mActivity, HistoryAndCollectionActivity.class);
            intent.putExtra(HistoryAndCollectionActivity.IS_HISTORY, false);
            mActivity.startActivity(intent);
        });
        mOrderView.setOnClickListener((up_view) -> UIUtil.showToast(mActivity, "我的订单"));
        mBindView.setOnClickListener((up_view) -> UIUtil.showToast(mActivity, "绑定管理"));
        mVipView.setOnClickListener((up_view) -> UIUtil.showToast(mActivity, "会员中心"));
        mSuggestionView.setOnClickListener((up_view) -> UIUtil.showToast(mActivity, "意见反馈"));
        mQuestionView.setOnClickListener((up_view) -> UIUtil.showToast(mActivity, "常见问题"));
        mAboutView.setOnClickListener((up_view) -> UIUtil.showToast(mActivity, "关于我们"));
        mSetView.setOnClickListener((up_view) -> UIUtil.showToast(mActivity, "设置"));

    }
}

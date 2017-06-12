package com.pbtd.mobile.widget;

import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.BaseActivity;
import com.pbtd.mobile.adapter.FullTvSelectAdapter;

/**
 * Created by xuqinchao on 17/5/9.
 */

public class FullScreenPoP extends PopupWindow {
    private final View conentView;
    private BaseActivity mContext;
    private GridView mGridView;
    private FullTvSelectAdapter mAdapter;
    private Listener mListener;

    public FullScreenPoP(BaseActivity context, int count) {
        mContext = context;
        conentView = View.inflate(context, R.layout.item_episodes_popwindow, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(h);
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

        initView(count);
    }

    private void initView(int count) {
        mGridView = (GridView) conentView.findViewById(R.id.gd);
        mAdapter = new FullTvSelectAdapter(mContext, count);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null)
                    mListener.onClick(position);
            }
        });
    }

    public interface Listener{
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }
    private void doAlphaAnimator(){
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

package com.pbtd.mobile.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.canyinghao.canrefresh.CanRefresh;

/**
 * Created by xuqinchao on 17/5/31.
 */

public class NoFooterView extends FrameLayout implements CanRefresh {


    public NoFooterView(@NonNull Context context) {
        super(context);
    }

    public NoFooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoFooterView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onReset() {

    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onPositionChange(float currentPercent) {

    }

    @Override
    public void setIsHeaderOrFooter(boolean isHeader) {

    }
}

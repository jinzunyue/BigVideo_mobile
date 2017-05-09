package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.utils.UIUtil;

/**
 * Created by xuqinchao on 17/5/9.
 */

public class FullTvSelectAdapter extends BaseListAdapter<String> {
    private int mCount;
    public FullTvSelectAdapter(Context mContext, int count) {
        super(mContext);
        mCount = count;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            TextView tv = new TextView(mContext);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    UIUtil.convertDpToPixel(mContext, 40), UIUtil.convertDpToPixel(mContext, 40));
            tv.setLayoutParams(layoutParams);
            tv.setBackgroundResource(R.drawable.select_tv_select);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(13);
            tv.setTextColor(mContext.getResources().getColor(R.color.white));
            convertView = tv;
        }
        ((TextView)convertView).setText(position+"");
        return convertView;
    }

}

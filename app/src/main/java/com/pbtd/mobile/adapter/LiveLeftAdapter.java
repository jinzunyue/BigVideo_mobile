package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pbtd.mobile.R;

/**
 * Created by xuqinchao on 17/5/2.
 */

public class LiveLeftAdapter extends BaseListAdapter<String> {

    boolean mIsBig;
    private int mCurrentSelectPosition;
    /**
     * 直播页面左侧类别适配器
     * @param mContext
     * @param isBig isBig == true 节目单， false 电视台
     */
    public LiveLeftAdapter(Context mContext, boolean isBig) {
        super(mContext);
        mIsBig = isBig;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, mIsBig?R.layout.item_live_left_big:R.layout.item_live_left, null);
            viewHolder = new ViewHolder();
            viewHolder.mTv = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTv.setText(mDatas.get(position));
        viewHolder.mTv.setSelected(position==mCurrentSelectPosition);
        return convertView;
    }

    public void setCurrentSelctPosition(int position) {
        mCurrentSelectPosition = position;
        notifyDataSetChanged();
    }

    public class ViewHolder{
        public TextView mTv;
    }
}

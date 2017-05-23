package com.pbtd.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.model.live.WeekProgramModel;
import com.pbtd.mobile.utils.StringUtil;

/**
 * Created by xuqinchao on 17/5/15.
 */

public class LiveProgramAdapter extends BaseListAdapter<WeekProgramModel> {

    private int mCurrentSelectPosition;

    public LiveProgramAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_live_program, null);
            viewHolder = new ViewHolder();
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mDate = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.mStatus = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WeekProgramModel weekProgramModel = mDatas.get(position);

        viewHolder.mTitle.setText(weekProgramModel.getEpgName());
        String startDate = weekProgramModel.getStartDate();
        startDate = "20" + startDate;
        startDate = startDate.replace("/", "-");
        long startTime = weekProgramModel.getStartTime();
        String time = StringUtil.formatTime(startTime*1000);
        viewHolder.mDate.setText(startDate + "  " + time + ":00");

        viewHolder.mDate.setSelected(position== mCurrentSelectPosition);
        viewHolder.mTitle.setSelected(position== mCurrentSelectPosition);

        if (weekProgramModel.getStartTime() * 1000 > System.currentTimeMillis()) {
            viewHolder.mStatus.setText(weekProgramModel.getSubscribe()==1?"已预约":"预约");
        } else {
            viewHolder.mStatus.setText(position== mCurrentSelectPosition ?"回看播放":"回看");
        }
        return convertView;
    }

    public void setCurrentSelectPosition(int position) {
        mCurrentSelectPosition = position;
        notifyDataSetChanged();
    }

    public void setStatusText() {}

    public class ViewHolder {
        public TextView mTitle;
        public TextView mDate;
        public TextView mStatus;
    }
}

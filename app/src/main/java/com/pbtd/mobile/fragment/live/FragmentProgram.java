package com.pbtd.mobile.fragment.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pbtd.mobile.Constants;
import com.pbtd.mobile.R;
import com.pbtd.mobile.adapter.LiveLeftAdapter;
import com.pbtd.mobile.adapter.LiveProgramAdapter;
import com.pbtd.mobile.fragment.BaseFragment;
import com.pbtd.mobile.model.live.WeekProgramModel;
import com.pbtd.mobile.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by xuqinchao on 17/5/11.
 */

public class FragmentProgram extends BaseFragment{

    private ListView mRightGridView;
    private ListView mLeftGridView;
    private LiveLeftAdapter mLeftAdapter;
    private LiveProgramAdapter mRightAdapter;
    private Listener mListener;
    private int mLeftCurrentPosition = 1;
    private int mRightCurrentPosition;
    private List<String> mDateList = new ArrayList<>();
    private HashMap<String, List<WeekProgramModel>> mDataMap = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = "节目单";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_program, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLeftGridView = (ListView) view.findViewById(R.id.list_left);
        mRightGridView = (ListView) view.findViewById(R.id.list_right);

        mLeftAdapter = new LiveLeftAdapter(mActivity, true);
        mLeftGridView.setAdapter(mLeftAdapter);
        mRightAdapter = new LiveProgramAdapter(mActivity);
        mRightGridView.setAdapter(mRightAdapter);

        List<String> list = new ArrayList<>();
        list.add("明天");
        list.add("今天");
        list.add(StringUtil.convertDate(StringUtil.getCurrentDate(-1)));
        list.add(StringUtil.convertDate(StringUtil.getCurrentDate(-2)));
        list.add(StringUtil.convertDate(StringUtil.getCurrentDate(-3)));
        list.add(StringUtil.convertDate(StringUtil.getCurrentDate(-4)));
        list.add(StringUtil.convertDate(StringUtil.getCurrentDate(-5)));
        for (int i = 0; i < list.size(); i++) {
            mDataMap.put(list.get(i), new ArrayList<WeekProgramModel>());
        }

        mLeftAdapter.setDatas(list);

        mLeftGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLeftAdapter.setCurrentSelctPosition(position);

                mRightAdapter.setDatas(mDataMap.get(mLeftAdapter.getItem(position)));
                mRightAdapter.setCurrentSelectPosition(0);
            }
        });

        mRightGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mRightAdapter.getDatas().get(position).getStartTime()*1000 > System.currentTimeMillis()) {
                    int subscribe = mRightAdapter.getDatas().get(position).getSubscribe();
                    mRightAdapter.getDatas().get(position).setSubscribe(subscribe ^ 1);
                    mRightAdapter.notifyDataSetChanged();
                } else {
                    mRightAdapter.setCurrentSelectPosition(position);
                    mRightAdapter.notifyDataSetChanged();

                    if (mListener != null) {
                        mListener.onClick(Constants.CCTV_9);
                    }
                }
            }
        });
    }

    public void setDatas(List<WeekProgramModel> list) {
        if(mRightAdapter==null) {
            return;
        }

        if(list ==null || list.size()==0) return;
        mRightAdapter.setDatas(list);

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
                    return (int) (o1.getStartTime()-o2.getStartTime());
                }
            });
        }
        mRightAdapter.setDatas(mDataMap.get("今天"));
        mRightAdapter.setCurrentSelectPosition(0);
        mLeftAdapter.setCurrentSelctPosition(1);
    }

    public interface Listener {
        void onClick(String url);
    }
    public void setListener(Listener listener) {
        mListener = listener;
    }
}

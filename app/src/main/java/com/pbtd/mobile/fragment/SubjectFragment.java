package com.pbtd.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.pbtd.mobile.R;
import com.pbtd.mobile.activity.SubjectDetailActivity;
import com.pbtd.mobile.adapter.SubjectAdapter;
import com.pbtd.mobile.model.temp.SubjectModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xuqinchao on 17/6/9.
 */

public class SubjectFragment extends BaseFragment {

    private Unbinder mUnbind;
    @BindView(R.id.iv_back)
    ImageView mBackView;
    @BindView(R.id.lv)
    ListView mListView;
    private SubjectAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject, null);
        mUnbind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mAdapter = new SubjectAdapter(mActivity);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mActivity, SubjectDetailActivity.class));
            }
        });

        List<SubjectModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SubjectModel model = new SubjectModel();
            model.description = "测试描述" + i;
            model.title = "测试标题" + i;
            list.add(model);
        }
        mAdapter.setDatas(list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbind.unbind();
    }
}

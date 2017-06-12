package com.pbtd.mobile.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pbtd.mobile.R;
import com.pbtd.mobile.adapter.SubjectDetailAdapter;
import com.pbtd.mobile.model.temp.SubjectDetailModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectDetailActivity extends AppCompatActivity {

    @BindView(R.id.sd_background)
    SimpleDraweeView mSDBackground;
    @BindView(R.id.iv_back)
    ImageView mBackView;
    @BindView(R.id.grid_view)
    GridView mGridView;
    private SubjectDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mAdapter = new SubjectDetailAdapter(this);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        List<SubjectDetailModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SubjectDetailModel model = new SubjectDetailModel();
            model.tip = "更新至" + i + "集";
            model.title = "标题" + i;
            list.add(model);
        }

        mAdapter.setDatas(list);
    }
}

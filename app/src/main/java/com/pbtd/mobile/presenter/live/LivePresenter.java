package com.pbtd.mobile.presenter.live;

import android.content.Context;

import com.pbtd.mobile.presenter.main.MainContract;

/**
 * Created by xuqinchao on 17/5/4.
 */

public class LivePresenter implements LiveContract.Presenter {

    private final MainContract.View mView;
    private final Context mContext;

    public LivePresenter(Context context, MainContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getCategoryList() {
//        RetrofitUtil.getInstance("https://api.starschina.com/appKey=ZjNmMjc2ODViOTgy&appOs=Android&osVer=4.4.4&appVer=1.0")
//                .getRequestApi().getLiveCategoryList()
    }
}

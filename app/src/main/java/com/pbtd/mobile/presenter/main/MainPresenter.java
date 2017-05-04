package com.pbtd.mobile.presenter.main;

import android.content.Context;

import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.CategoryModel;
import com.pbtd.mobile.network.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xuqinchao on 17/5/4.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mView;
    private final Context mContext;

    public MainPresenter(Context context, MainContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getCategoryList() {
        RetrofitUtil.getInstance().getRequestApi().getCategoryList()
                .enqueue(new Callback<BaseModel<List<CategoryModel>>>() {
                    @Override
                    public void onResponse(Call<BaseModel<List<CategoryModel>>> call, Response<BaseModel<List<CategoryModel>>> response) {
                        BaseModel<List<CategoryModel>> body = response.body();
                        if (body.success == true) {
                            mView.showCategoryList(body.result);
                        } else {
                            String message = body.message;
                            mView.showError(message);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<List<CategoryModel>>> call, Throwable t) {

                    }
                });
    }
}

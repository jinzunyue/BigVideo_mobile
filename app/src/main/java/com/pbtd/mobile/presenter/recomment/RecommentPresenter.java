package com.pbtd.mobile.presenter.recomment;

import android.content.Context;

import com.pbtd.mobile.model.RecommendModel;
import com.pbtd.mobile.network.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class RecommentPresenter implements RecommentContract.Presenter {

    private Context mContext;
    private RecommentContract.View mView;

    public RecommentPresenter(Context context, RecommentContract.View decorView) {
            mContext = context;
            mView = decorView;
    }

    @Override
    public void getRecomment(int type_id) {
        if (type_id == 0) {
            RetrofitUtil.getInstance(false).getRequestApi().getRecommentNoParam()
                    .enqueue(new Callback<List<RecommendModel>>() {
                        @Override
                        public void onResponse(Call<List<RecommendModel>> call, Response<List<RecommendModel>> response) {
                            mView.setRecomment(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<RecommendModel>> call, Throwable t) {

                        }
                    });
        }else {
            RetrofitUtil.getInstance(false).getRequestApi().getRecomment(type_id)
                    .enqueue(new Callback<List<RecommendModel>>() {
                        @Override
                        public void onResponse(Call<List<RecommendModel>> call, Response<List<RecommendModel>> response) {
                            mView.setRecomment(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<RecommendModel>> call, Throwable t) {

                        }
                    });
        }

    }
}

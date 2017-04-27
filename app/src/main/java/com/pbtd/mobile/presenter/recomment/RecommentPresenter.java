package com.pbtd.mobile.presenter.recomment;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pbtd.mobile.Constants;
import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.ProductInfoListModel;
import com.pbtd.mobile.model.RecommendVideoModel;
import com.pbtd.mobile.network.RetrofitUtil;
import com.pbtd.mobile.utils.SharedPreferencesUtil;

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
//        if (type_id == 0) {
//            RetrofitUtil.getInstance().getRequestApi().getRecommentNoParam()
//                    .enqueue(new Callback<List<RecommendModel>>() {
//                        @Override
//                        public void onResponse(Call<List<RecommendModel>> call, Response<List<RecommendModel>> response) {
//                            mView.setRecomment(response.body());
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<RecommendModel>> call, Throwable t) {
//
//                        }
//                    });
//        }else {
//            RetrofitUtil.getInstance(false).getRequestApi().getRecomment(type_id)
//                    .enqueue(new Callback<List<RecommendModel>>() {
//                        @Override
//                        public void onResponse(Call<List<RecommendModel>> call, Response<List<RecommendModel>> response) {
//                            mView.setRecomment(response.body());
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<RecommendModel>> call, Throwable t) {
//
//                        }
//                    });
//        }

    }

    @Override
    public void getRecommentVideo(@NonNull String recommend_code) {
        String serviceComboCode = SharedPreferencesUtil.getServiceComboCode(mContext);
        RetrofitUtil.getInstance("http://oms01.ott.guttv.cibntv.net/oms_api/").getRequestApi().getRecommendVideo(recommend_code, serviceComboCode)
                .enqueue(new Callback<BaseModel<RecommendVideoModel>>() {
                    @Override
                    public void onResponse(Call<BaseModel<RecommendVideoModel>> call, Response<BaseModel<RecommendVideoModel>> response) {
                        BaseModel<RecommendVideoModel> body = response.body();
                        if (body !=null && "0000".equals(body.retCode)) {
                            RecommendVideoModel retMsg = body.retMsg;
                            mView.showRecommendVideo(retMsg);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<RecommendVideoModel>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void getProductInfoList(@NonNull String package_code) {
        RetrofitUtil.getInstance("http://vod01.ott.guttv.cibntv.net/vod_api/").getRequestApi().getProductInfoList(Constants.SERVICE_GROUP_CODE, package_code, 50, 0, 1)
                .enqueue(new Callback<BaseModel<ProductInfoListModel>>() {
                    @Override
                    public void onResponse(Call<BaseModel<ProductInfoListModel>> call, Response<BaseModel<ProductInfoListModel>> response) {
                        if (response.body() != null && response.body().retCode.equals("00000000")) {
                            mView.showProductInfoList(response.body().retMsg.listInfo);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<ProductInfoListModel>> call, Throwable t) {

                    }
                });
    }


}

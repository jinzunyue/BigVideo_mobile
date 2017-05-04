package com.pbtd.mobile.presenter.play;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pbtd.mobile.Constants;
import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.PlayInfoModel;
import com.pbtd.mobile.model.ProductInfoListModel;
import com.pbtd.mobile.network.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class PlayPresenter implements PlayContract.Presenter {

    private Context mContext;
    private PlayContract.View mView;

    public PlayPresenter(Context context, PlayContract.View decorView) {
            mContext = context;
            mView = decorView;
    }


    @Override
    public void getPlayInfo(@NonNull String product_code) {
        RetrofitUtil.getInstance("http://vod01.ott.guttv.cibntv.net/vod_api/").getRequestApi().
                getPlayInfoModel(
                Constants.DOMAIN_CODE,
                Constants.SERVICE_GROUP_CODE,
                Constants.USER_CODE,
                product_code)
                .enqueue(new Callback<BaseModel<PlayInfoModel>>() {

                    @Override
                    public void onResponse(Call<BaseModel<PlayInfoModel>> call, Response<BaseModel<PlayInfoModel>> response) {
                        if (response.body() != null && response.body().retCode.equals("00000000")) {
                            mView.showPlayInfo(response.body().retMsg);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<PlayInfoModel>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void getRelativeProductInfoList(@NonNull String packageCodes, @NonNull String productCode, @NonNull String pageLimit, @NonNull String pageNum) {
        RetrofitUtil.getInstance("http://vod01.ott.guttv.cibntv.net/vod_api/").getRequestApi()
                .getRelativeProductInfoList(Constants.USER_CODE,
                        Constants.SERVICE_GROUP_CODE,
                        packageCodes, productCode, "2", "2", "6", "1")
                .enqueue(new Callback<BaseModel<ProductInfoListModel>>() {
                    @Override
                    public void onResponse(Call<BaseModel<ProductInfoListModel>> call, Response<BaseModel<ProductInfoListModel>> response) {
                        if (response.body().retCode.equals("00000000") && response.body().retMsg != null) {
                            mView.showRelativeProductInfoList(response.body().retMsg);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<ProductInfoListModel>> call, Throwable t) {

                    }
                });

    }


}

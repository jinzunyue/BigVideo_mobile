package com.pbtd.mobile.presenter.launcher;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.InitModel;
import com.pbtd.mobile.network.RetrofitUtil;
import com.pbtd.mobile.utils.MacUtil;
import com.pbtd.mobile.utils.SharedPreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xuqinchao on 17/4/24.
 */

public class LauncherPresenter implements LauncherContract.Presenter {

    private Context mContext;
    private LauncherContract.View mView;
    private Gson mGson;

    public LauncherPresenter(Context context, LauncherContract.View decorView) {
        mContext = context;
        mView = decorView;

        mGson = new Gson();
    }

    @Override
    public void doAuth() {
        String wifiMacAddress = MacUtil.getMacAddress(mContext);
        RetrofitUtil.getInstance("http://120.221.38.130/oms_api/").getRequestApi().getAuth(wifiMacAddress)
                .enqueue(new Callback<BaseModel<String>>() {
                    @Override
                    public void onResponse(Call<BaseModel<String>> call, Response<BaseModel<String>> response) {
                        BaseModel<String> body = response.body();
                        if (body != null) {
                            if ("00000000".equals(body.retCode)) {
                                String msg = body.retMsg;
                                if (!TextUtils.isEmpty(msg)){
                                    SharedPreferencesUtil.saveComboCode(mContext, msg);
                                    doInit(msg);
                                }
                            } else {
                                mView.showError("认证失败" + response.body().retMsg);
                            }
                        } else {
                            mView.showError("初始化失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<String>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void doInit(String comboCode) {
        RetrofitUtil.getInstance("http://120.221.38.130/oms_api/").getRequestApi().getInit(MacUtil.getMacAddress(mContext), comboCode)
        .enqueue(new Callback<BaseModel<InitModel>>() {
            @Override
            public void onResponse(Call<BaseModel<InitModel>> call, Response<BaseModel<InitModel>> response) {
                BaseModel<InitModel> body = response.body();
                if (body != null) {
                    if ("00000000".equals(body.retCode)) {
                        String serviceComboCode = body.retMsg.serviceComboCode;
                        SharedPreferencesUtil.saveServiceComboCode(mContext, serviceComboCode);
                        mView.showSuccess();
                    } else {
                        mView.showError("认证失败" + response.body().retMsg);
                    }
                } else {
                    mView.showError("初始化失败");
                }
            }

            @Override
            public void onFailure(Call<BaseModel<InitModel>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
}

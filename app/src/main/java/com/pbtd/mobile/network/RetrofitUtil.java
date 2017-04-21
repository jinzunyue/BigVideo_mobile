package com.pbtd.mobile.network;

import com.pbtd.mobile.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/5/11.
 */
public class RetrofitUtil {
    private static RetrofitUtil mRetrofitUtil;
    private Retrofit retrofit;

    private RetrofitUtil(boolean isUser) {
        retrofit = new Retrofit.Builder()
                .baseUrl(isUser?Constants.BASE_USER_SERVER:Constants.BASE_VIDEO_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory()
                .build();
    }

    public static RetrofitUtil getInstance (boolean isUser) {
        if (mRetrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (mRetrofitUtil == null)
                    mRetrofitUtil = new RetrofitUtil(isUser);
            }
        }

        return mRetrofitUtil;
    }

    public APIService getRequestApi () {
        return retrofit.create(APIService.class);
    }
}

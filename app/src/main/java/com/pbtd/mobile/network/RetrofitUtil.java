package com.pbtd.mobile.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/5/11.
 */
public class RetrofitUtil {
    private static RetrofitUtil mRetrofitUtil;
    private Retrofit retrofit;

    private RetrofitUtil(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory()
                .build();
    }

    public static RetrofitUtil getInstance (String url) {
        if (mRetrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (mRetrofitUtil == null)
                    mRetrofitUtil = new RetrofitUtil(url);
            }
        }

        return mRetrofitUtil;
    }

    public APIService getRequestApi () {
        return retrofit.create(APIService.class);
    }
}

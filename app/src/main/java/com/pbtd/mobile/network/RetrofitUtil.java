package com.pbtd.mobile.network;

import com.pbtd.mobile.Constants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/5/11.
 */
public class RetrofitUtil {
    private static RetrofitUtil mRetrofitUtil;
    private Retrofit retrofit;

    private RetrofitUtil() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url();
                        System.out.println(Constants.GLOBAL_TAG + url);
                        Response response = chain.proceed(request);
                        return response;
                    }
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
//                .addCallAdapterFactory()
                .build();
    }

    public static RetrofitUtil getInstance () {
        if (mRetrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (mRetrofitUtil == null)
                    mRetrofitUtil = new RetrofitUtil();
            }
        }

        return mRetrofitUtil;
    }

    public APIService getRequestApi () {
        return retrofit.create(APIService.class);
    }
}

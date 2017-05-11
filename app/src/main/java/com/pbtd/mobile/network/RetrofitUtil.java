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

    private RetrofitUtil(String base_url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url();
                        System.out.println(Constants.LOGGER_TAG + url);
                        Response response = chain.proceed(request);
                        return response;
                    }
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
//                .addCallAdapterFactory()
                .build();
    }

    public static RetrofitUtil getInstance (String base_url) {
        if (mRetrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (mRetrofitUtil == null)
                    mRetrofitUtil = new RetrofitUtil(base_url);
            }
        }

        return mRetrofitUtil;
    }

    public APIService getRequestApi () {
        return retrofit.create(APIService.class);
    }
}

package com.pbtd.mobile.network;

import com.pbtd.mobile.model.RecommendModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface APIService {

    @GET("php_recommvideo.php")
    Call<List<RecommendModel>> getRecomment(@Query("type_id") int type_id);

    @GET("php_recommvideo.php")
    Call<List<RecommendModel>> getRecommentNoParam();
}

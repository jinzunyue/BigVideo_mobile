package com.pbtd.mobile.network;

import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.CategoryModel;
import com.pbtd.mobile.model.ProductDetailModel;
import com.pbtd.mobile.model.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface APIService {

    @GET("CIBN_APP/albumsAction!getCategoryList.action")
    Call<BaseModel<List<CategoryModel>>> getCategoryList();

    @GET("CIBN_APP/albumsAction!getRecommendList.action")
    Call<BaseModel<List<ProductModel>>> getProductList(@Query("type_id")String type_id,
                                                 @Query("start")String start, @Query("limit")String limit);

    @GET("CIBN_APP/albumsAction!getAlbumsList.action")
    Call<BaseModel<List<ProductDetailModel>>> getProductDetail(@Query("seriesCode")String seriesCode);

    @GET("CIBN_APP/albumsAction!getRelateList.action")
    Call<BaseModel<List<ProductModel>>> getRelativeProduct(@Query("seriesCode")String seriesCode);


}

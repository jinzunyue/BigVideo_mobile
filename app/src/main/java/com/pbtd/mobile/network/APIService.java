package com.pbtd.mobile.network;

import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.CategoryListModel;
import com.pbtd.mobile.model.InitModel;
import com.pbtd.mobile.model.NavigationInfoModel;
import com.pbtd.mobile.model.PlayInfoModel;
import com.pbtd.mobile.model.ProductInfoListModel;
import com.pbtd.mobile.model.RecommendVideoModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface APIService {

//    @GET("http://120.221.38.130/oms_api/device!deviceAuth")
//    Call<BaseModel<String>> getAuth(@Query("mac")String mac);
//
//    @GET("http://120.221.38.130/oms_api/device!deviceInit")
//    Call<BaseModel<InitModel>> getInit(@Query("mac")String mac, @Query("comboCode")String comboCode);
//
//    @GET("http://oms01.ott.guttv.cibntv.net/oms_api/serviceGroup!getNavigationInfo")
//    Call<BaseModel<NavigationInfoModel>> getNavigationInfo(@Query("serviceComboCode")String serviceComboCode);
//
//    @GET("http://oms01.ott.guttv.cibntv.net/oms_api/recommend!getRecommendInfo")
//    Call<BaseModel<RecommendVideoModel>> getRecommendVideo(@Query("recommendCode")String recommendCode, @Query("comboCode")String comboCode);
//
//    @GET("http://vod01.ott.guttv.cibntv.net/vod_api/category!getCategoryList")
//    Call<BaseModel<CategoryListModel>> getCategoryListModel(@Query("serviceGroupCode")String serviceGroupCode);
//
//    @GET("http://vod01.ott.guttv.cibntv.net/vod_api/assetList!getProductList")
//    Call<BaseModel<ProductInfoListModel>> getProductInfoList(@Query("serviceGroupCode")String serviceGroupCode,
//                                                             @Query("packageCodes")String packageCodes,
//                                                             @Query("pageLimit")int pageLimit, @Query("pageNum")int pageNum,
//                                                             @Query("isAll")int isAll);
//
//    @GET("http://vod01.ott.guttv.cibntv.net/vod_api/seriesInfo!getSeriesInfoByCode")
//    Call<BaseModel<PlayInfoModel>> getPlayInfoModel(@Query("domainCodes")String domainCodes,
//                                                    @Query("serviceGroupCode")String serviceGroupCode,
//                                                    @Query("userCode")String userCode,
//                                                    @Query("productCode")String productCode
//                                                    );

    @GET("device!deviceAuth")//http://120.221.38.130/oms_api/
    Call<BaseModel<String>> getAuth(@Query("mac")String mac);

    @GET("device!deviceInit")
    Call<BaseModel<InitModel>> getInit(@Query("mac")String mac, @Query("comboCode")String comboCode);

    @GET("serviceGroup!getNavigationInfo")//http://oms01.ott.guttv.cibntv.net/oms_api/
    Call<BaseModel<NavigationInfoModel>> getNavigationInfo(@Query("serviceComboCode")String serviceComboCode);

    @GET("recommend!getRecommendInfo")
    Call<BaseModel<RecommendVideoModel>> getRecommendVideo(@Query("recommendCode")String recommendCode, @Query("comboCode")String comboCode);

    @GET("category!getCategoryList")//http://vod01.ott.guttv.cibntv.net/vod_api/
    Call<BaseModel<CategoryListModel>> getCategoryListModel(@Query("serviceGroupCode")String serviceGroupCode);

    @GET("assetList!getProductList")
    Call<BaseModel<ProductInfoListModel>> getProductInfoList(@Query("serviceGroupCode")String serviceGroupCode,
                                                             @Query("packageCodes")String packageCodes,
                                                             @Query("pageLimit")int pageLimit, @Query("pageNum")int pageNum,
                                                             @Query("isAll")int isAll);

    @GET("seriesInfo!getSeriesInfoByCode")
    Call<BaseModel<PlayInfoModel>> getPlayInfoModel(@Query("domainCodes")String domainCodes,
                                                    @Query("serviceGroupCode")String serviceGroupCode,
                                                    @Query("userCode")String userCode,
                                                    @Query("productCode")String productCode
    );
}

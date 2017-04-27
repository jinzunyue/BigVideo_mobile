package com.pbtd.mobile.presenter.main;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pbtd.mobile.model.BaseModel;
import com.pbtd.mobile.model.CategoryListModel;
import com.pbtd.mobile.model.CategoryModel;
import com.pbtd.mobile.model.NavigationInfoModel;
import com.pbtd.mobile.model.PlayInfoModel;
import com.pbtd.mobile.model.RecommendVideoModel;
import com.pbtd.mobile.network.RetrofitUtil;
import com.pbtd.mobile.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xuqinchao on 17/4/19.
 */

public class MainPresenter implements MainContract.Presenter {

    private Context mContext;
    private MainContract.View mView;
    private List<CategoryModel> mCategoryModelList = new ArrayList<>();

    private HashMap<String, List<PlayInfoModel>> mResultData = new HashMap<>();

    public MainPresenter(Context context, MainContract.View decorView) {
        mContext = context;
        mView = decorView;
    }

    @Override
    public void getNavigationInfo() {
        String serviceComboCode = SharedPreferencesUtil.getServiceComboCode(mContext);
        RetrofitUtil.getInstance("http://oms01.ott.guttv.cibntv.net/oms_api/").getRequestApi().getNavigationInfo(serviceComboCode)
                .enqueue(new Callback<BaseModel<NavigationInfoModel>>() {
                    @Override
                    public void onResponse(Call<BaseModel<NavigationInfoModel>> call, Response<BaseModel<NavigationInfoModel>> response) {
                        BaseModel<NavigationInfoModel> body = response.body();
                        if (body != null && "00000000".equals(body.retCode)) {
//                            mView.showNavigation(body.retMsg);
                            NavigationInfoModel retMsg = body.retMsg;
                            List<NavigationInfoModel.NavigationItem> navigationItemList = retMsg.navigationItem;
                            for (int i = 0; i < navigationItemList.size(); i++) {
                                NavigationInfoModel.NavigationItem navigationItem = navigationItemList.get(i);
                                if ("OPEN_PROGRAM_LIST".equals(navigationItem.action)) {

                                }

                                if ("OPEN_DETAIL".equals(navigationItem.action)) {

                                }
                            }
                        } else {
                            mView.showError("获取数据失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<NavigationInfoModel>> call, Throwable t) {

                    }
                });
    }

    public void getRecommentVideo(@NonNull String recommend_code) {
        String serviceComboCode = SharedPreferencesUtil.getServiceComboCode(mContext);
        RetrofitUtil.getInstance("http://oms01.ott.guttv.cibntv.net/oms_api/").getRequestApi().getRecommendVideo(recommend_code, serviceComboCode)
                .enqueue(new Callback<BaseModel<RecommendVideoModel>>() {
                    @Override
                    public void onResponse(Call<BaseModel<RecommendVideoModel>> call, Response<BaseModel<RecommendVideoModel>> response) {
                        BaseModel<RecommendVideoModel> body = response.body();
                        if (body !=null && "0000".equals(body.retCode)) {
                            RecommendVideoModel retMsg = body.retMsg;
                            List<RecommendVideoModel.RecommendContent> recommendContent = retMsg.recommendContent;
                            List<PlayInfoModel> productInfoModelList = new ArrayList<PlayInfoModel>();
                            for (int i = 0; i < recommendContent.size(); i++) {
                                RecommendVideoModel.RecommendContent recommendContent1 = recommendContent.get(i);
                                if ("OPEN_DETAIL".equals(recommendContent1.action)) {
                                    PlayInfoModel productInfoModel = new PlayInfoModel();
                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<RecommendVideoModel>> call, Throwable t) {

                    }
                });
    }

    public void getCategoryList() {
        String serviceComboCode = SharedPreferencesUtil.getServiceComboCode(mContext);
        RetrofitUtil.getInstance("http://vod01.ott.guttv.cibntv.net/vod_api/").getRequestApi().getCategoryListModel(serviceComboCode)
                .enqueue(new Callback<BaseModel<CategoryListModel>>() {
                    @Override
                    public void onResponse(Call<BaseModel<CategoryListModel>> call, Response<BaseModel<CategoryListModel>> response) {
                        BaseModel<CategoryListModel> body = response.body();
                        if (body != null && "00000000".equals(body.retCode)) {
                            List<CategoryListModel.CategoryContentModel> list = body.retMsg.list;
                            for (int i = 0; i < list.size(); i++) {
                                CategoryListModel.CategoryContentModel categoryContentModel = list.get(i);
                                if (categoryContentModel.equals("1")) {
                                    mCategoryModelList = categoryContentModel.categoryItem;
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel<CategoryListModel>> call, Throwable t) {

                    }
                });
    }
}

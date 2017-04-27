package com.pbtd.mobile.presenter.recomment;

import android.support.annotation.NonNull;

import com.pbtd.mobile.model.ProductInfoModel;
import com.pbtd.mobile.model.RecommendModel;
import com.pbtd.mobile.model.RecommendVideoModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface RecommentContract {
    interface Presenter {
        void getRecomment(int type_id);
        void getRecommentVideo(@NonNull String recommend_code);
        void getProductInfoList(@NonNull String package_code);
    }

    interface View {
        void setRecomment(List<RecommendModel> list);
        void showRecommendVideo(RecommendVideoModel model);
        void showProductInfoList(List<ProductInfoModel> list);
    }
}

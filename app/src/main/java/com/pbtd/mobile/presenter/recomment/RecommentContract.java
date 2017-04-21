package com.pbtd.mobile.presenter.recomment;

import com.pbtd.mobile.model.RecommendModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface RecommentContract {
    interface Presenter {
        void getRecomment(int type_id);
    }

    interface View {
        void setRecomment(List<RecommendModel> list);
    }
}

package com.pbtd.mobile.presenter.live;

import com.pbtd.mobile.model.live.CategoryInnerModel;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/19.
 */

public interface LiveContract {
    interface Presenter {
        void getCategoryList();
        void getProgramOfWeek(String videoId);
    }

    interface View {
        void showError(String error);
        void showCategoryList(List<CategoryInnerModel> list);
    }
}

package com.pbtd.mobile.model;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/26.
 */

public class CategoryListModel {
    public List<CategoryContentModel> list;

    public class CategoryContentModel {
        public String parentCode;
        public String level;// level == 1 需要的栏目
        public List<CategoryModel> categoryItem;
    }
}

package com.pbtd.mobile.model.temp;

import com.pbtd.mobile.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqinchao on 17/5/4.
 */

public class RecommendModel {
    private String name;
    private List<ProductModel> list = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductModel> getList() {
        return list;
    }

    public void setList(List<ProductModel> list) {
        this.list = list;
    }
}

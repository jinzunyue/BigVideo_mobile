package com.pbtd.mobile.model;

import java.util.List;

/**
 * 推荐位数据
 * Created by xuqinchao on 17/4/1.
 */

public class RecommendModel {
    private String typeID;//推荐位id
    private List<RecommendedVideo> recommendedVideos;//推荐视频信息

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public List<RecommendedVideo> getRecommendedVideos() {
        return recommendedVideos;
    }

    public void setRecommendedVideos(List<RecommendedVideo> recommendedVideos) {
        this.recommendedVideos = recommendedVideos;
    }

}

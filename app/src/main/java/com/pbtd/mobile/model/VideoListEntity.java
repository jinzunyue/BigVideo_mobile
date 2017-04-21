package com.pbtd.mobile.model;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/1.
 */

public class VideoListEntity {
    private String totalRecordCount;//总播放次数
    private List<VideoDetailEntity> videoList;

    public String getTotalRecordCount() {
        return totalRecordCount;
    }

    public void setTotalRecordCount(String totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }

    public List<VideoDetailEntity> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoDetailEntity> videoList) {
        this.videoList = videoList;
    }

}

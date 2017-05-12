package com.pbtd.mobile.model.live;

import java.util.List;

/**
 * Created by xuqinchao on 17/5/12.
 */

public class LiveCategoryModel {

    private List<DataModel> datas;

    public List<DataModel> getDatas() {
        return datas;
    }

    public void setDatas(List<DataModel> datas) {
        this.datas = datas;
    }

    public class DataModel {
        private List<InnerDataModel> data;

        public List<InnerDataModel> getData() {
            return data;
        }

        public void setData(List<InnerDataModel> data) {
            this.data = data;
        }
    }

    public class InnerDataModel {
        private String videoName;
        private int videoId;
        private String area;
        private String videoImage;
        private String cpName;

        public InnerDataModel() {
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public int getVideoId() {
            return videoId;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getVideoImage() {
            return videoImage;
        }

        public void setVideoImage(String videoImage) {
            this.videoImage = videoImage;
        }

        public String getCpName() {
            return cpName;
        }

        public void setCpName(String cpName) {
            this.cpName = cpName;
        }
    }
}

package com.pbtd.mobile.model;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/24.
 */

public class RecommendVideoModel {
    public String description;
    public String name;
    public String code;
    public List<RecommendContent> recommendContent;

    public class RecommendContent {
        public String idx;
        public String code;
        public String contentType;
        public String serviceType;
        public String posterUrl;
        public String assetCode;
        public String recommendCode;
        public String showName;
        public String assetType;
        public String description;
        public String posterType;
        public String recommendPositionCode;
        public String name;
        public String action;
        public String displayIndex;
    }
}

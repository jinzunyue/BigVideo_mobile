package com.pbtd.mobile.model;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/24.
 */

public class NavigationInfoModel {
    public String serviceComboCode;
    public String navigationCode;
    public List<NavigationItem> navigationItem;

    public class NavigationItem {
        public String code;
        public String name;
        public String sequence;
        public String packageName;
        public String action;
        public String serviceType;
        public String recommendCode;
        public String categoryCode;
    }
}

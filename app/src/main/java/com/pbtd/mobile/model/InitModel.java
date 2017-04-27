package com.pbtd.mobile.model;

import java.util.List;

/**
 * Created by xuqinchao on 17/4/24.
 */

public class InitModel {
    public String userCode;
    public String deviceCode;
    public String domainCode;
    public String customerInfo;//客服信息
    public String serviceComboCode;//业务组合code

    public List<ServiceGroup> serviceGroupList;
    public List<Api> apiList;

    class ServiceGroup {
        public String serviceGroupCode;//业务分组code
        public String serviceType;
    }

    class Api {
        public String name;
        public String value;
    }
}

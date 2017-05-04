package com.pbtd.mobile.model;

/**
 * Created by xuqinchao on 17/5/4.
 */

public class ProductModel {
    private String packageCode;
    private String pictureurl1;
    private String seriesCode;
    private String seriesName;

    public String getProgramtype() {
        return programtype;
    }

    public void setProgramtype(String programtype) {
        this.programtype = programtype;
    }

    private String programtype;

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getPictureurl1() {
        return pictureurl1;
    }

    public void setPictureurl1(String pictureurl1) {
        this.pictureurl1 = pictureurl1;
    }

    public String getSeriesCode() {
        return seriesCode;
    }

    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}

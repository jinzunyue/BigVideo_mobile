package com.pbtd.mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuqinchao on 17/5/4.
 * 没有全部解析
 */

public class ProductDetailModel implements Parcelable{
    private String seriesName;//剧名
    private String programName;//本集名称
    private String actorDisplay;//导演
    private String writerDisplay;//主演
    private String description;
    private String keyWord;
    private String language;
    private String originalCountry;//发布国家
    private String orgairDate;//上线时间
    private String movieList;// TODO: 17/5/4 数据格式
    private String programType2;// 电影类型

    protected ProductDetailModel(Parcel in) {
        seriesName = in.readString();
        programName = in.readString();
        actorDisplay = in.readString();
        writerDisplay = in.readString();
        description = in.readString();
        keyWord = in.readString();
        language = in.readString();
        originalCountry = in.readString();
        orgairDate = in.readString();
        movieList = in.readString();
        programType2 = in.readString();
    }

    public static final Creator<ProductDetailModel> CREATOR = new Creator<ProductDetailModel>() {
        @Override
        public ProductDetailModel createFromParcel(Parcel in) {
            return new ProductDetailModel(in);
        }

        @Override
        public ProductDetailModel[] newArray(int size) {
            return new ProductDetailModel[size];
        }
    };

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getActorDisplay() {
        return actorDisplay;
    }

    public void setActorDisplay(String actorDisplay) {
        this.actorDisplay = actorDisplay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOriginalCountry() {
        return originalCountry;
    }

    public void setOriginalCountry(String originalCountry) {
        this.originalCountry = originalCountry;
    }

    public String getOrgairDate() {
        return orgairDate;
    }

    public void setOrgairDate(String orgairDate) {
        this.orgairDate = orgairDate;
    }

    public String getMovieList() {
        return movieList;
    }

    public void setMovieList(String movieList) {
        this.movieList = movieList;
    }

    public String getProgramType2() {
        return programType2;
    }

    public void setProgramType2(String programType2) {
        this.programType2 = programType2;
    }

    public String getWriterDisplay() {
        return writerDisplay;
    }

    public void setWriterDisplay(String writerDisplay) {
        this.writerDisplay = writerDisplay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(seriesName);
        dest.writeString(programName);
        dest.writeString(actorDisplay);
        dest.writeString(writerDisplay);
        dest.writeString(description);
        dest.writeString(keyWord);
        dest.writeString(language);
        dest.writeString(originalCountry);
        dest.writeString(orgairDate);
        dest.writeString(movieList);
        dest.writeString(programType2);
    }
}

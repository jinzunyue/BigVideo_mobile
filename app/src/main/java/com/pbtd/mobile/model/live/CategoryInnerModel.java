package com.pbtd.mobile.model.live;

/**
 * Created by xuqinchao on 17/5/12.
 */

public class CategoryInnerModel {

    private String videoName;
    private int videoId;
    private String area;
    private String videoImage;
    private String cpName;
    private ProgramTimeModel mTimeProgram;

    public ProgramTimeModel getmTimeProgram() {
        return mTimeProgram;
    }

    public void setmTimeProgram(ProgramTimeModel mTimeProgram) {
        this.mTimeProgram = mTimeProgram;
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

package com.pbtd.mobile.model;

import java.util.ArrayList;

/**
 * 视频所有的分集、电视剧指定集数、综艺节目的分集
 * videoSource   ??
 * Created by xuqinchao on 17/4/1.
 */

public class EpisodesEntity {
    private String ID;//视频id
    private String name;//视频名称
    private String description;//简介
    private String columnID;//类目id（视频分类   4：电视剧   5：电影  3：综艺）
    private String runTime;//播放时长（单位：秒）
    private String yearReleased;//年代
    private String playCount;//播放次数
    private String imageURL;//图片路径
    private String episodeNumber; // 级数
    private ArrayList<PlayInfo> playInfo;//播放信息

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColumnID() {
        return columnID;
    }

    public void setColumnID(String columnID) {
        this.columnID = columnID;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(String yearReleased) {
        this.yearReleased = yearReleased;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ArrayList<PlayInfo> getPlayInfo() {
        return playInfo;
    }

    public void setPlayInfo(ArrayList<PlayInfo> playInfo) {
        this.playInfo = playInfo;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }
}

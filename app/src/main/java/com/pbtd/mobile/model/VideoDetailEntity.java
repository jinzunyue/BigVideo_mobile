package com.pbtd.mobile.model;

import java.util.ArrayList;

/**
 * 点播详情
 * Created by xuqinchao on 17/4/1.
 */

public class VideoDetailEntity {
    private String ID;//视频id
    private String name;//视频名称
    private String columnID;//类目id（视频分类   4：电视剧   5：电影  3：综艺）
    private String shortDescription;//短简介
    private String longDescription;//长简介
    private String active;//是否为有效视频
    private String timeCreated;//生成时间
    private String createdBy;//从哪里导入
    private String playCount;//播放次数
    private String isFree;//是否免费
    private String avgRating;//评分
    private String runTime;//播放时长（单位：秒）
    private String yearReleased;//年代
    private String channelID;//播放时长（单位：秒）
    private String isPush;//是否推送
    private int totalEpisodes;//总集数
    private String oldestEpisodeNumber;//总集数
    private String lastestEpisodeNumber;//最新的一集
    private String latestEpisodeNumber;//上一集
    private String images;//图片路径
    private String[] tags;//类型
    private ArrayList<PlayInfo> playInfo;//播放信息
    private String imageURL;

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

    public String getColumnID() {
        return columnID;
    }

    public void setColumnID(String columnID) {
        this.columnID = columnID;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
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

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getIsPush() {
        return isPush;
    }

    public void setIsPush(String isPush) {
        this.isPush = isPush;
    }

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    public String getOldestEpisodeNumber() {
        return oldestEpisodeNumber;
    }

    public void setOldestEpisodeNumber(String oldestEpisodeNumber) {
        this.oldestEpisodeNumber = oldestEpisodeNumber;
    }

    public String getLastestEpisodeNumber() {
        return lastestEpisodeNumber;
    }

    public void setLastestEpisodeNumber(String lastestEpisodeNumber) {
        this.lastestEpisodeNumber = lastestEpisodeNumber;
    }

    public String getLatestEpisodeNumber() {
        return latestEpisodeNumber;
    }

    public void setLatestEpisodeNumber(String latestEpisodeNumber) {
        this.latestEpisodeNumber = latestEpisodeNumber;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public ArrayList<PlayInfo> getPlayInfo() {
        return playInfo;
    }

    public void setPlayInfo(ArrayList<PlayInfo> playInfo) {
        this.playInfo = playInfo;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

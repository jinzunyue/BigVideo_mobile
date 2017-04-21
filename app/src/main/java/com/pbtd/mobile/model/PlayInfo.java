package com.pbtd.mobile.model;

import java.io.Serializable;

/**
 * 综艺节目播放链接
 * videoSource  暂不解析
 * Created by xuqinchao on 17/4/1.
 */

public class PlayInfo implements Serializable{
    private String playURL;//播放连接
    private String videoAspect;//视频码率
    private String videoRatio;//视频播放比例
    private String protocol;//播放ip类型
    private String format;//播放类型
    private VideoSource videoSource;//

    public String getPlayURL() {
        return playURL;
    }

    public void setPlayURL(String playURL) {
        this.playURL = playURL;
    }

    public String getVideoAspect() {
        return videoAspect;
    }

    public void setVideoAspect(String videoAspect) {
        this.videoAspect = videoAspect;
    }

    public String getVideoRatio() {
        return videoRatio;
    }

    public void setVideoRatio(String videoRatio) {
        this.videoRatio = videoRatio;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

//    public VideoSource getVideoSource() {
//        return videoSource;
//    }
//
//    public void setVideoSource(VideoSource videoSource) {
//        this.videoSource = videoSource;
//    }

    class VideoSource implements Serializable{
        private String iconUrl;//图标路径
        private String type;//播放类型
        private String name;//播放名称
        private String sourceID;//资源id

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSourceID() {
            return sourceID;
        }

        public void setSourceID(String sourceID) {
            this.sourceID = sourceID;
        }
    }
}

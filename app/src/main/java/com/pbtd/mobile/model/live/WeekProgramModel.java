package com.pbtd.mobile.model.live;

/**
 * Created by xuqinchao on 17/5/15.
 * epgId: 36378796,//节目单 ID
 * epgName: "晚间气象服务",//节目名称
 * showId: 0,
 showName: "",
 startDate: "15/12/21",//节目单日期
 weekDay: "星期一",//星期
 startTime: 1450630800,//开始时间
 endTime: 1450631220,//结束时间
 duration: 420,//节目播放时长
 videoId: 1,
 description: "",//节目描述
 createdAt: 1450648833,//节目单创建时间
 updatedAt: 1450648833//节目单修改时间
 subscribe：客户端临时定义字段    1：已预约    0：未预约
 */

public class WeekProgramModel {
    private String epgName;
    private String startDate;
    private String weekDay;
    private long startTime;
    private long endTime;
    private int subscribe;

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public String getEpgName() {
        return epgName;
    }

    public void setEpgName(String epgName) {
        this.epgName = epgName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    private long duration;
}

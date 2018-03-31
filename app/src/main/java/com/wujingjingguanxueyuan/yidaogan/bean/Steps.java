package com.wujingjingguanxueyuan.yidaogan.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2018/3/26.
 */

public class Steps extends BmobObject{
    private String date;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer step;
    private String heat_Str;
    private String distance_Str;
    public User user;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getHeat_Str() {
        return heat_Str;
    }

    public void setHeat_Str(String heat_Str) {
        this.heat_Str = heat_Str;
    }

    public String getDistance_Str() {
        return distance_Str;
    }

    public void setDistance_Str(String distance_Str) {
        this.distance_Str = distance_Str;
    }
}

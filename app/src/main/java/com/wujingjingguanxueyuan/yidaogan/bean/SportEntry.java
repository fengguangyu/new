package com.wujingjingguanxueyuan.yidaogan.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by ${Karoline} on 2018/3/2.
 */

public class SportEntry extends BmobObject{
    public String date;
    public Integer year;
    public Integer month;
    public Integer day;
    public Integer sport_steps = 0; //实际步数
    public String sport_distance;
    public String sport_heat;
    public Integer do_hint;
    public Integer show_hint;
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


    public String getSport_heat() {
        return sport_heat;
    }

    public void setHeat_Str(String heat_Str) {
        this.sport_heat = sport_heat;
    }

    public String getSport_distance() {
        return sport_distance;
    }

    public void setDistance_Str(String distance_Str) {
        this.sport_distance = sport_distance;
    }

    public Integer getSport_steps(){
        return sport_steps;
    }

    public void setSport_steps(Integer sport_steps){
        this.sport_steps = sport_steps;
    }
}

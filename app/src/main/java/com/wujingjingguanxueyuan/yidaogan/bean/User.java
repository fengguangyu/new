package com.wujingjingguanxueyuan.yidaogan.bean;

import android.text.TextUtils;

import com.wujingjingguanxueyuan.yidaogan.db.NewFriend;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author :smile
 * @project:User
 * @date :2016-01-22-18:11
 */
public class User extends BmobUser {

    private BmobFile avatar;
    private String gender;
    private String nick;
    private String birthday;
    private String birth_year;
    private String birth_month;
    private String birth_day;
    private String height_str;
    private String height;
    private String age;
    private String weight = 50+"";
    private String weight_str;
    private String length = 70+"";
    private String length_str;
    private String count;
    public Integer step_plan; //计划步数
    public String plan_start_date;
    public Integer plan_start_year;
    public Integer plan_start_month;
    public Integer plan_start_day;
    public String plan_stop_date;
    public Integer plan_stop_year;
    public Integer plan_stop_month;
    public Integer plan_stop_day;
    public Float plan_min_normal_weight_values;
    public Float plan_max_normal_weight_values;
    public Integer plan_want_weight_values;

    public User() {
    }


        public User(NewFriend friend) {
        setObjectId(friend.getUid());
        setUsername(friend.getName());
        setAvatar(new BmobFile(new File(friend.getAvatar())));
    }


    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNick() {
        return TextUtils.isEmpty(nick)?"未填写": nick ;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getBirth_month() {
        return birth_month;
    }

    public void setBirth_month(String birth_month) {
        this.birth_month = birth_month;
    }

    public String getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(String birth_day) {
        this.birth_day = birth_day;
    }

    public String getHeight_str() {
        return height_str;
    }

    public void setHeight_str(String height_str) {
        this.height_str = height_str;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeight_str() {
        return weight_str;
    }

    public void setWeight_str(String weight_str) {
        this.weight_str = weight_str;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getLength_str() {
        return length_str;
    }

    public void setLength_str(String length_str) {
        this.length_str = length_str;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Integer getStep_plan() {
        return step_plan;
    }

    public void setStep_plan(Integer step_plan){
        this.step_plan = step_plan;
    }

    public String getPlan_stop_date(){
        return plan_stop_date;
    }

    public void setPlan_stop_date(String plan_stop_date){
        this.plan_stop_date = plan_stop_date;
    }

    public String getPlan_start_date(){
        return plan_start_date;
    }
    public void setPlan_start_date(String plan_start_date){
        this.plan_start_date = plan_start_date;
    }

    public Integer getPlan_want_weight_values() {
        return plan_want_weight_values;
    }

    public void s(Integer step_plan){
        this.step_plan = step_plan;
    }

}

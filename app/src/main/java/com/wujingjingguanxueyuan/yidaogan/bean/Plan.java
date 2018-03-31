package com.wujingjingguanxueyuan.yidaogan.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by ${Karoline} on 2018/3/2.
 */

public class Plan extends BmobObject{
    public Integer sport_type;
    public String sport_name;
    public Integer start_year;
    public Integer start_month;
    public Integer start_day;
    public Integer stop_year;
    public Integer stop_month;
    public Integer stop_day;
    public Long set_time;

    public Long hint_time;
    public String hint_str;
    public Integer hint_hour;
    public Integer hint_minute;
    public Integer number_values;
    public Integer add_24_hour;
    public User user;

}

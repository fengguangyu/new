package com.wujingjingguanxueyuan.yidaogan.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by ${Karoline} on 2018/3/2.
 */

public class SportEntry extends BmobObject{
    public Integer sport_steps = 0; //实际步数
    public String sport_distance;
    public String sport_heat;
    public Integer do_hint;
    public Integer show_hint;
    public User user;
}

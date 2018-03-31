package com.wujingjingguanxueyuan.yidaogan.bean;

/**
 * Created by ${Karoline} on 2018/3/2.
 */

public class Wrapper {
    public static Wrapper instance;
    private User user;
    public static Wrapper getInstance(){
        if(instance == null){
            instance = new Wrapper();
        }
        return  instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

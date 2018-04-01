package com.wujingjingguanxueyuan.yidaogan.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.wujingjingguanxueyuan.yidaogan.bean.Steps;
import com.wujingjingguanxueyuan.yidaogan.bean.User;
import com.wujingjingguanxueyuan.yidaogan.db.DatasDao;
import com.wujingjingguanxueyuan.yidaogan.receiver.FunctionBroadcastReceiver;
import com.wujingjingguanxueyuan.yidaogan.utils.DateUtils;
import com.wujingjingguanxueyuan.yidaogan.utils.SaveKeyValues;
import com.wujingjingguanxueyuan.yidaogan.utils.StepDetector;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 记录保存服务(此处用于记录值)
 */
public class RecordedSaveService extends Service {
    public static final String cancelSaveService = "com.wujingjingguanxueyuan.yidaogan.RECORDED";
    private DatasDao datasDao;
    public RecordedSaveService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("测试服务","启动了！");
        int custom_step_length = SaveKeyValues.getIntValues("length",70);
        int custom_weight = SaveKeyValues.getIntValues("weight", 50);
        //创建数据库工具类
        datasDao = new DatasDao(this);
        boolean result = isActivityRunning(this);
        int step;
        //判断如何存储数值
        if (result){//获取步数直接获取
            step = StepDetector.CURRENT_SETP;
        }else {//获取xml存值加啥记录步数
            step = StepDetector.CURRENT_SETP + SaveKeyValues.getIntValues("sport_steps" ,0);
        }
        //通过步数计算消耗的热量和行走的路程
        double distance_values = step * custom_step_length * 0.01 *0.001;//km
        String distance_Str = formatDouble(distance_values);
        double heat_values = custom_weight * distance_values * 1.036;//cls
        String heat_Str = formatDouble(heat_values);
        //获取日期
        Map<String,Object> map = DateUtils.getDate();
        int year = (int) map.get("year");
        int month = (int) map.get("month");
        int day = (int) map.get("day");
        String date = (String) map.get("date");
        //存入数据
        Steps steps = new Steps();
        steps.setDate(date);
        steps.setYear(year);
        steps.setMonth(month);
        steps.setDay(day);
        steps.setStep(step);
        steps.setDistance_Str(distance_Str);
        steps.user = BmobUser.getCurrentUser(User.class);
        steps.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    SaveKeyValues.putIntValues("sport_steps", 0 );
                    StepDetector.CURRENT_SETP = 0;
                    Intent bro = new Intent(RecordedSaveService.this, FunctionBroadcastReceiver.class);
                    bro.setAction(cancelSaveService);
                    sendBroadcast(bro);
                }
            }
        });
        /*ContentValues values = new ContentValues();
        values.put("date",date);
        values.put("year",year);
        values.put("month",month);
        values.put("day",day);
        values.put("steps",step);
        values.put("hot",heat_Str);
        values.put("length", distance_Str);
        long reBack = datasDao.insertValue("step",values);
        if (reBack > 0){
            SaveKeyValues.putIntValues("sport_steps", 0 );
            StepDetector.CURRENT_SETP = 0;
            Intent bro = new Intent(this, FunctionBroadcastReceiver.class);
            bro.setAction(cancelSaveService);
            sendBroadcast(bro);
        }*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("测试服务", "结束了！");
    }

    /**
     * 用判断activity是否在运行
     * @param mContext
     * @return
     */
    public static boolean isActivityRunning(Context mContext){
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info = activityManager.getRunningTasks(1);
        if(info != null && info.size() > 0){
            ComponentName component = info.get(0).topActivity;
            if(component.getPackageName().equals(mContext.getPackageName())){
                return true;
            }
        }
        return false;
    }
    private String formatDouble(Double doubles) {
        DecimalFormat format = new DecimalFormat("####.##");
        String distanceStr = format.format(doubles);
        return distanceStr.equals("0") ? "0.00" : distanceStr;
    }
}

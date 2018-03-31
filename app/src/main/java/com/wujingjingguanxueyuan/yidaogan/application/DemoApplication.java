package com.wujingjingguanxueyuan.yidaogan.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.base.UniversalImageLoader;
import com.wujingjingguanxueyuan.yidaogan.utils.BringData;
import com.wujingjingguanxueyuan.yidaogan.utils.GetLocation;
import com.wujingjingguanxueyuan.yidaogan.utils.SaveKeyValues;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cn.bmob.newim.BmobIM;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2016/5/25.
 */
public class DemoApplication extends Application {
    public static Bitmap[] bitmaps = new Bitmap[5];
    public static String[] shuoming = new String[5];
    private static DemoApplication INSTANCE;


    public static DemoApplication INSTANCE() {
        return INSTANCE;
    }

    private void setInstance(DemoApplication app) {
        setBmobIMApplication(app);
    }

    private static void setBmobIMApplication(DemoApplication a) {
        DemoApplication.INSTANCE = a;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        //定位
        new GetLocation(getApplicationContext());
        //实例化sharedPreferences
        SaveKeyValues.createSharePreferences(this);
        bitmaps[0] = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.mrkj_fushen1);
        bitmaps[1] = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.mipmap.mrkj_fuwocheng1);
        bitmaps[2] = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.mipmap.mrkj_gunlun1);
        bitmaps[3] = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.mipmap.mrkj_wotui1);
        bitmaps[4] = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.mipmap.mrkj_sanwanju1);

        shuoming[0] = "俯身哑铃飞鸟";
        shuoming[1] = "俯卧撑";
        shuoming[2] = "滚轮支点俯卧撑";
        shuoming[3] = "平板卧推";
        shuoming[4] = "仰卧平板杠铃肱三弯举";
        //将食物热量
        int saveDateIndex = SaveKeyValues.getIntValues("date_index",0);
        Log.e("数据库数否被存入", "【" + saveDateIndex + "】");
        if (saveDateIndex == 0){
            try {
                SaveKeyValues.putIntValues("date_index", 1);
                BringData.getDataFromAssets(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        DBHelper dbHelper = new DBHelper();
//        Cursor cursor = dbHelper.selectAllDataOfTable("hot");
//        while (cursor.moveToNext()){
//            String name = cursor.getString(cursor.getColumnIndex("name"));
//            String type_name = cursor.getString(cursor.getColumnIndex("type_name"));
//            Log.e("食物","【" + name + "】" + "、"+"【" + type_name + "】");
//        }
//        cursor.close();
        super.onCreate();
        setInstance(this);
        //TODO 集成：1.8、初始化IM SDK，并注册消息接收器，只有主进程运行的时候才需要初始化
        Bmob.initialize(this,"8655e4bc5bd9f9f1626ec77e94307c82");
        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
            BmobIM.init(this);
            BmobIM.registerDefaultMessageHandler(new DemoMessageHandler(this));
            super.onCreate();
            /**
             * 保存设备信息，用于推送功能
             */
            BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
                @Override
                public void done(BmobInstallation bmobInstallation, BmobException e) {
                    if (e == null) {
                        Logger.i(bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());
                    } else {
                        Logger.e(e.getMessage());
                    }
                }
            });
            /**
             * 启动推送服务
             */
            BmobPush.startWork(this);
        }
        Logger.init("BmobNewIMDemo");
        UniversalImageLoader.initImageLoader(this);
    }
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.wujingjingguanxueyuan.yidaogan.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.application.DemoApplication;
import com.wujingjingguanxueyuan.yidaogan.bean.Plan;
import com.wujingjingguanxueyuan.yidaogan.bean.User;
import com.wujingjingguanxueyuan.yidaogan.utils.Constant;
import com.wujingjingguanxueyuan.yidaogan.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscriber;

/**
 * 添加计划的界面
 */
public class SettingHealthyHealthyActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener, View.OnClickListener {

    private TextView back;
    private TextView title;//运动类型
    private int type;
    private String title_name;
    private TimePicker timePicker;//设置时间
    private int alarmhour;//提示时
    private int alarmminute;//提示分
    private DatePickerDialog datePickerDialog;
    private Button start, stop;
    private int index;//用于区分获取开始还是结束
    private int start_year,start_month,start_day,stop_year,stop_month,stop_day;
    private Button finish_setting;
    //存入数据
   // private DatasDao datasDao;
    private boolean isToSave;

    private List<Plan> planList = new ArrayList<>();
    private Plan plan = null;
    int isAdd_24_hours = 0;
    long nowTime;
    long wantSaveTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_healthy_healthy);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);// 设置布局填充activity界面

       // datasDao = new DatasDao(this);
        Intent intent = getIntent();

        BmobQuery<Plan> planBmobQuery = new BmobQuery<>();
        planBmobQuery.findObjectsObservable(Plan.class).subscribe(new Subscriber<List<Plan>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Plan> plans) {
                planList.clear();
                planList.addAll(plans);

            }
        });


        //返回
        back = (TextView) findViewById(R.id.to_back);
        back.setOnClickListener(this);
        //类型
        title = (TextView) findViewById(R.id.plan_type);
        type = intent.getIntExtra("type", 0);
        title_name = DemoApplication.shuoming[type];
        title.setText(title_name);
        //时间
        timePicker = (TimePicker) findViewById(R.id.timePicker1);
        timePicker.setOnTimeChangedListener(this);
        //日期
        Map<String, Object> timeMap = DateUtils.getDate();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                
                switch (index){
                    case 0://start
                        start_year = year;
                        start_month = monthOfYear + 1;
                        start_day = dayOfMonth;
                        start.setText("起始："+start_year + "-" + start_month + "-" + start_day);
                        break;
                    case 1://stop
                        stop_year = year;
                        stop_month = monthOfYear + 1;
                        stop_day = dayOfMonth;
                        stop.setText("结束："+stop_year + "-" + stop_month + "-" + stop_day);
                        break;
                    default:
                        break;

                }
            }
        }, (Integer) timeMap.get("year"), (Integer) timeMap.get("month") - 1, (Integer) timeMap.get("day"));
        start = (Button) findViewById(R.id.plan_start);
        stop = (Button) findViewById(R.id.plan_stop);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        //完成
        finish_setting = (Button) findViewById(R.id.set_clock);
        finish_setting.setOnClickListener(this);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_back://返回
                finish();
                break;
            case R.id.plan_start://设置开始日期
                index = 0;
                datePickerDialog.setTitle("设置开始时间");
                datePickerDialog.show();
                break;
            case R.id.plan_stop://设置结束日期
                index = 1;
                datePickerDialog.setTitle("设置结束时间");
                datePickerDialog.show();
                break;
            case R.id.set_clock://设置完成
                Log.e("分割","====================");
//                Log.e("分割","");
               isAdd_24_hours = 0;
                nowTime = DateUtils.getMillisecondValues((int) DateUtils.getDate().get("hour"), (int) DateUtils.getDate().get("minute"));
                //先对要存入的数值做一下处理
                wantSaveTime = DateUtils.getMillisecondValues(alarmhour,alarmminute);
                String selectID = "";
//                Log.e("增加前", wantSaveTime + "");
                if (wantSaveTime <= nowTime ){
                    wantSaveTime += Constant.DAY_FOR_24_HOURS;
                    isAdd_24_hours = 1;
//                    Log.e("执行了？","加上了24个小时");
                }
                //没设置结束时间
                if ( start_year != 0 && stop_year == 0){
                    stop_year = start_year;
                    stop_month = start_month;
                    stop_day = start_day;
                }
                //没设置开始时间
                if( start_year == 0 && stop_year != 0){
                    start_year = (int) DateUtils.getDate().get("year");
                    start_month = (int) DateUtils.getDate().get("month");
                    start_day = (int) DateUtils.getDate().get("day");
                }
                //没实质计划时间
                if(start_year == 0 && stop_year == 0){
                    start_year = (int) DateUtils.getDate().get("year");
                    start_month = (int) DateUtils.getDate().get("month");
                    start_day = (int) DateUtils.getDate().get("day");
                    stop_year = start_year;
                    stop_month = start_month;
                    stop_day = start_day;
                }
                //设置的时间不合法
                if (DateUtils.getMillisecondValues(start_year,start_month,start_day) > DateUtils.getMillisecondValues(stop_year,stop_month,stop_day)){
                    Toast.makeText(this,"开始时间不得大于结束时间！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (DateUtils.getMillisecondValues((Integer) DateUtils.getDate().get("year"),(int) DateUtils.getDate().get("month"),(int) DateUtils.getDate().get("day")) > DateUtils.getMillisecondValues(stop_year,stop_month,stop_day)){
                    Toast.makeText(this,"结束时间不得小于当前时间！", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*final ContentValues values = new ContentValues();
                //1.存入运动类型
                values.put("sport_type" , type);//1
                //2.存入运动类型名称
                values.put("sport_name" , title_name);//2
                //3.存入开始年月日
                values.put("start_year" , start_year);//3
                values.put("start_month" , start_month);//4
                values.put("start_day" , start_day);//5
                //4.存入结束年月日
                values.put("stop_year" , stop_year);//6
                values.put("stop_month" , stop_month);//7
                values.put("stop_day" , stop_day);//8
                //5.存入设置的时间
                values.put("set_time", nowTime);//9*/


                //6.存入提醒时间
                if (alarmminute == 0 && alarmhour == 0){
                    alarmhour = (int) DateUtils.getDate().get("hour");
                    alarmminute = (int) DateUtils.getDate().get("minute");
                }
                /*values.put("hint_time" , wantSaveTime);//10
//                Log.e("想要设置的时间", wantSaveTime + "");
                values.put("hint_str" , alarmhour + "点" + alarmminute + "分");
                values.put("hint_hour",alarmhour);
                values.put("hint_minute",alarmminute);
                //7.存入顺序
                values.put("number_values" , 0);
                values.put("add_24_hour",isAdd_24_hours);*/

                final Plan plan1 = null;
                if(planList.size() >0){
                    for(int i=0;i<planList.size();i++){
                        if(wantSaveTime == planList.get(i).hint_time){
                            selectID = planList.get(i).getObjectId();
                            plan = planList.get(i);
                            break;
                        }
                    }

                    if (!TextUtils.isEmpty(selectID)) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                        dialog.setTitle("提示");
                        dialog.setMessage("改时间点已被占用，是否要覆盖改时间点的运动计划！");

                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                plan.sport_type = type;
                                plan.sport_name = title_name;
                                plan.start_year = start_year;
                                plan.start_month = start_month;
                                plan.start_day = start_day;
                                plan.stop_year = stop_year;
                                plan.stop_month = stop_month;
                                plan.stop_day = stop_day;
                                plan.set_time = nowTime;
                                plan.hint_time = wantSaveTime;
                                plan.hint_str = alarmhour + "点" + alarmminute + "分";
                                plan.hint_hour = alarmhour;
                                plan.hint_minute = alarmminute;
                                plan.number_values = 0;
                                plan.add_24_hour = isAdd_24_hours;
                                plan.user = (User) BmobUser.getCurrentUser();
                                isToSave = true;
                                plan.update();
                                //insertData(plan);
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isToSave = false;
                                Toast.makeText(SettingHealthyHealthyActivity.this, "取消设置计划！", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.create();
                        dialog.show();
                    }else{
                        isToSave = true;
                        plan = new Plan();
                        plan.sport_type = type;
                        plan.sport_name = title_name;
                        plan.start_year = start_year;
                        plan.start_month = start_month;
                        plan.start_day = start_day;
                        plan.stop_year = stop_year;
                        plan.stop_month = stop_month;
                        plan.stop_day = stop_day;
                        plan.set_time = nowTime;
                        plan.hint_time = wantSaveTime;
                        plan.hint_str = alarmhour + "点" + alarmminute + "分";
                        plan.hint_hour = alarmhour;
                        plan.hint_minute = alarmminute;
                        plan.number_values = 0;
                        plan.add_24_hour = isAdd_24_hours;
                        plan.user = BmobUser.getCurrentUser(User.class);
                        insertData(plan);
                    }
                }else {
                    isToSave = true;
                    plan = new Plan();
                    plan.sport_type = type;
                    plan.sport_name = title_name;
                    plan.start_year = start_year;
                    plan.start_month = start_month;
                    plan.start_day = start_day;
                    plan.stop_year = stop_year;
                    plan.stop_month = stop_month;
                    plan.stop_day = stop_day;
                    plan.set_time = nowTime;
                    plan.hint_time = wantSaveTime;
                    plan.hint_str = alarmhour + "点" + alarmminute + "分";
                    plan.hint_hour = alarmhour;
                    plan.hint_minute = alarmminute;
                    plan.number_values = 0;
                    plan.add_24_hour = isAdd_24_hours;
                    plan.user = (User) BmobUser.getCurrentUser();
                    insertData(plan);
                }
                break;
            default:
                break;
        }
    }

    private void insertData(Plan values){
        if (isToSave){
            //values.user = (User) BmobUser.getCurrentUser();
            //向数据库中插入数据
            values.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        Toast.makeText(SettingHealthyHealthyActivity.this,"设置计划成功！", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }else {
                        Toast.makeText(SettingHealthyHealthyActivity.this,"设置计划失败！请重新设置计划！", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
    /**
     * 时间改变回调
     *
     * @param view
     * @param hourOfDay
     * @param minute
     */
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//        Log.e("hourOfDay",hourOfDay + "时");
//        Log.e("minute", minute + " 分");
        alarmhour = hourOfDay;
        alarmminute = minute;
    }
}
